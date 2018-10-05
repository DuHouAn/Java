### ipc.Server几个内部类：
* Call ：
    
    用于存储客户端发来的请求
* Listener ： 

    监听类，用于监听客户端发来的请求，同时Listener内部还有一个静态类，
	Listener.Reader，当监听器监听到用户请求，便让Reader读取用户请求。
* Responder ：
    
    响应RPC请求类，请求处理完毕，由Responder发送给请求客户端。
* Connection ：
    
    连接类，真正的客户端请求读取逻辑在这个类中。
* Handler ：
    
    请求处理类，会循环阻塞读取callQueue中的call对象，并对其进行操作。

```java
private void initialize(Configuration conf) throws IOException {
    //...
    // 创建 rpc server
    InetSocketAddress dnSocketAddr = getServiceRpcServerAddress(conf);
    if (dnSocketAddr != null) {
      int serviceHandlerCount =
        conf.getInt(DFSConfigKeys.DFS_NAMENODE_SERVICE_HANDLER_COUNT_KEY,
                    DFSConfigKeys.DFS_NAMENODE_SERVICE_HANDLER_COUNT_DEFAULT);
      //获得serviceRpcServer
      this.serviceRpcServer = RPC.getServer(this, dnSocketAddr.getHostName(), 
          dnSocketAddr.getPort(), serviceHandlerCount,
          false, conf, namesystem.getDelegationTokenSecretManager());
      this.serviceRPCAddress = this.serviceRpcServer.getListenerAddress();
      setRpcServiceServerAddress(conf);
   }
  //TODO:RPC的server对象是通过ipc.RPC类的getServer()方法获得的
    this.server = RPC.getServer(this, socAddr.getHostName(),
        socAddr.getPort(), handlerCount, false, conf, namesystem
        .getDelegationTokenSecretManager());
 
    //...
    this.server.start();  //启动 RPC server   Clients只允许连接该server
    if (serviceRpcServer != null) {
      serviceRpcServer.start();  //启动 RPC serviceRpcServer 为HDFS服务的server
    }
    startTrashEmptier(conf);
  }

public static Server getServer(final Object instance, final String bindAddress, final int port,
                                 final int numHandlers,
                                 final boolean verbose, Configuration conf,
                                 SecretManager<? extends TokenIdentifier> secretManager) 
    throws IOException {
    return new Server(instance, conf, bindAddress, port, numHandlers, verbose, secretManager);
  }
```

初始化Server后，Server端就运行起来了，看看ipc.Server的start()源码吧：

```java
/** 启动服务 */
 public synchronized void start() {
   responder.start();  //启动responder
   listener.start();   //启动listener
   handlers = new Handler[handlerCount];
 
   for (int i = 0; i < handlerCount; i++) {
     handlers[i] = new Handler(i);
     handlers[i].start();   //逐个启动Handler
   }
 }
 ```
 
分析过ipc.Client源码后，我们知道Client端的底层通信直接采用了阻塞式IO编程，
当时我们曾做出猜测：Server端是不是也采用了阻塞式IO。
现在我们仔细地分析一下吧，如果Server端也采用阻塞式IO，
当连接进来的Client端很多时，势必会影响Server端的性能。
hadoop的实现者们考虑到了这点，所以他们采用了java  NIO来实现Server端。
分析源码得知，Server端采用Listener监听客户端的连接，下面先分析一下Listener的构造函数:

```java
//Server.Listener的构造函数
public Listener() throws IOException { //TODO：很典型的NIO Server端
     address = new InetSocketAddress(bindAddress, port);
     // 创建ServerSocketChannel,并设置成非阻塞式
     acceptChannel = ServerSocketChannel.open();
     acceptChannel.configureBlocking(false);
 
     // 将server socket绑定到本地端口
     bind(acceptChannel.socket(), address, backlogLength);
     port = acceptChannel.socket().getLocalPort(); 
     // 获得一个selector
     selector= Selector.open();
     readers = new Reader[readThreads];
     readPool = Executors.newFixedThreadPool(readThreads);
     //启动多个reader线程，为了防止请求多时服务端响应延时的问题
     for (int i = 0; i < readThreads; i++) {       
       Selector readSelector = Selector.open();
       Reader reader = new Reader(readSelector);
       readers[i] = reader;
       readPool.execute(reader);
     }
     // 注册连接事件
     acceptChannel.register(selector, SelectionKey.OP_ACCEPT);
     this.setName("IPC Server listener on " + port);
     this.setDaemon(true);
   }
```

在启动Listener线程时，服务端会一直等待客户端的连接。

```java
//Server.Listener类的run()方法：
public void run() {
     //...
     while (running) {
       SelectionKey key = null;
       try {
         selector.select();
         Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
         while (iter.hasNext()) {
           key = iter.next();
           iter.remove();
           try {
             if (key.isValid()) {
               if (key.isAcceptable())
                 doAccept(key);     //具体的连接方法
             }
           } catch (IOException e) {
           }
           key = null;
         }
       } catch (OutOfMemoryError e) {
      //... 
   }
```

Server.Listener类中doAccept()方法:

```java
void doAccept(SelectionKey key) throws IOException,  OutOfMemoryError {
      Connection c = null;
      ServerSocketChannel server = (ServerSocketChannel) key.channel();
      SocketChannel channel;
      while ((channel = server.accept()) != null) { //建立连接
        channel.configureBlocking(false);
        channel.socket().setTcpNoDelay(tcpNoDelay);
        Reader reader = getReader();  //从readers池中获得一个reader
        try {
          reader.startAdd(); // 激活readSelector，设置adding为true
          SelectionKey readKey = reader.registerChannel(channel);//将读事件设置成兴趣事件
          c = new Connection(readKey, channel, System.currentTimeMillis());//创建一个连接对象
          readKey.attach(c);   //将connection对象注入readKey
          synchronized (connectionList) {
            connectionList.add(numConnections, c);
            numConnections++;
          }
        //...
        } finally {
//设置adding为false，采用notify()唤醒一个reader,其实代码十三中启动的每个reader都使
//用了wait()方法等待。因篇幅有限，就不贴出源码了。
          reader.finishAdd();
        }
      }
    }
```

当reader被唤醒，reader接着执行doRead()方法。
下面贴出Server.Listener.Reader类中的doRead()方法和Server.Connection类中的readAndProcess()方法源码：

```
void doRead(SelectionKey key) throws InterruptedException {
      int count = 0;
      Connection c = (Connection)key.attachment();  //获得connection对象
      if (c == null) {
        return;  
      }
      c.setLastContact(System.currentTimeMillis());
      try {
        count = c.readAndProcess();    // 接受并处理请求  
      } catch (InterruptedException ieo) {
      //...
      }
     //...    
}

public int readAndProcess() throws IOException, InterruptedException {
      while (true) {
        //...
        if (!rpcHeaderRead) {
          if (rpcHeaderBuffer == null) {
            rpcHeaderBuffer = ByteBuffer.allocate(2);
          }
         //读取请求头
          count = channelRead(channel, rpcHeaderBuffer);
          if (count < 0 || rpcHeaderBuffer.remaining() > 0) {
            return count;
          }
        // 读取请求版本号  
          int version = rpcHeaderBuffer.get(0);
          byte[] method = new byte[] {rpcHeaderBuffer.get(1)};
        //...
 
          data = ByteBuffer.allocate(dataLength);
        }
        // 读取请求  
        count = channelRead(channel, data);
 
        if (data.remaining() == 0) {
          //...
          if (useSasl) {
         //...
          } else {
            processOneRpc(data.array());//处理请求
          }
        //...
          }
        } 
        return count;
      }
    }
```

Server.Connection类中的processOneRpc()方法和processData()方法的源码:

```
private void processOneRpc(byte[] buf) throws IOException,
        InterruptedException {
      if (headerRead) {
        processData(buf);
      } else {
        processHeader(buf);
        headerRead = true;
        if (!authorizeConnection()) {
          throw new AccessControlException("Connection from " + this
              + " for protocol " + header.getProtocol()
              + " is unauthorized for user " + user);
        }
      }
}

private void processData(byte[] buf) throws  IOException, InterruptedException {
      DataInputStream dis =
        new DataInputStream(new ByteArrayInputStream(buf));
      int id = dis.readInt();      // 尝试读取id
      Writable param = ReflectionUtils.newInstance(paramClass, conf);//读取参数
      param.readFields(dis);        
 
      Call call = new Call(id, param, this);  //封装成call
      callQueue.put(call);   // 将call存入callQueue
      incRpcCount();  // 增加rpc请求的计数
    }
```

