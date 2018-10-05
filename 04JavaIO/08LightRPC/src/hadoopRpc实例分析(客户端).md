## ipc.RPC类中有一些内部类:
* Invocation ：

    用于封装方法名和参数，作为数据传输层。
* ClientCache ：

    用于存储client对象，用socket factory作为hash key,存储结构为hashMap <SocketFactory, Client>。
* Invoker ：
    
    是动态代理中的调用实现类，继承了InvocationHandler.
* Server ：
    
    是ipc.Server的实现类。

```java
public Object invoke(Object proxy, Method method, Object[] args)
      throws Throwable {
      //...
      ObjectWritable value = (ObjectWritable)
        client.call(new Invocation(method, args), remoteId);
      //...
      return value.get();
    }
```
在hadoop中，是将数据发送给服务端，服务端将处理的结果再返回给客户端，
所以这里的invoke()方法必然需要进行网络通信。

### 1、客户端和服务端的连接是怎样建立的？

```java
public Writable call(Writable param, ConnectionId remoteId)  
                       throws InterruptedException, IOException {
    Call call = new Call(param);       //将传入的数据封装成call对象
    Connection connection = getConnection(remoteId, call);   //获得一个连接
    connection.sendParam(call);     // 向服务端发送call对象
   //...
 }
```

ipc.Client类中的getConnection()方法:

```java
private Connection getConnection(ConnectionId remoteId,
                                   Call call)
                                   throws IOException, InterruptedException {
    if (!running.get()) {
      // 如果client关闭了
      throw new IOException("The client is stopped");
    }
    Connection connection;
//如果connections连接池中有对应的连接对象，就不需重新创建了；如果没有就需重新创建一个连接对象。
//但请注意，该连接对象只是存储了remoteId的信息，其实还并没有和服务端建立连接。
    do {
      synchronized (connections) {
        connection = connections.get(remoteId);
        if (connection == null) {
          connection = new Connection(remoteId);
          connections.put(remoteId, connection);
        }
      }
    } while (!connection.addCall(call)); //将call对象放入对应连接中的calls池，就不贴出源码了
   //这句代码才是真正的完成了和服务端建立连接哦
    connection.setupIOstreams();
    return connection;
  }
```

Client.Connection类中的setupIOstreams()方法：

```
private synchronized void setupIOstreams() throws InterruptedException {
    //... 
     try {
       //...
       while (true) {
         setupConnection();  //建立连接
         InputStream inStream = NetUtils.getInputStream(socket);     //获得输入流
         OutputStream outStream = NetUtils.getOutputStream(socket);  //获得输出流
         writeRpcHeader(outStream);
         //...
         this.in = new DataInputStream(new BufferedInputStream
             (new PingInputStream(inStream)));   //将输入流装饰成DataInputStream
         this.out = new DataOutputStream
         (new BufferedOutputStream(outStream));   //将输出流装饰成DataOutputStream
         writeHeader();
         // 跟新活动时间
         touch();
         //当连接建立时，启动接受线程等待服务端传回数据，注意：Connection继承了Tread
         start();
         return;
       }
     } catch (IOException e) {
       markClosed(e);
       close();
     }
   }
```

Client.Connection类中的setupConnection()方法：

```java
private synchronized void setupConnection() throws IOException {
     short ioFailures = 0;
     short timeoutFailures = 0;
     while (true) {
       try {
         this.socket = socketFactory.createSocket(); //终于看到创建socket的方法了
         //客户端的连接建立，就是通过创建一个普通的Socket进行通信。
         this.socket.setTcpNoDelay(tcpNoDelay);
         //...
         // 设置连接超时为20s
         NetUtils.connect(this.socket, remoteId.getAddress(), 20000);
         this.socket.setSoTimeout(pingInterval);
         return;
       } catch (SocketTimeoutException toe) {
         /* 设置最多连接重试为45次。
          * 总共有20s*45 = 15 分钟的重试时间。
          */
         handleConnectionFailure(timeoutFailures++, 45, toe);
       } catch (IOException ie) {
         handleConnectionFailure(ioFailures++, maxRetries, ie);
       }
     }
   }
```

### 2、客户端是怎样给服务端发送数据的？
```java
public void sendParam(Call call) {
      if (shouldCloseConnection.get()) {
        return;
      }
      DataOutputBuffer d=null;
      try {
        synchronized (this.out) {
          if (LOG.isDebugEnabled())
            LOG.debug(getName() + " sending #" + call.id);
          //创建一个缓冲区
          d = new DataOutputBuffer();
          d.writeInt(call.id);
          call.param.write(d);
          byte[] data = d.getData();
          int dataLength = d.getLength();
          out.writeInt(dataLength);        //首先写出数据的长度
          out.write(data, 0, dataLength); //向服务端写数据
          out.flush();
        }
      } catch(IOException e) {
        markClosed(e);
      } finally {
        IOUtils.closeStream(d);
      }
    }
```

### 3、客户端是怎样获取服务端的返回数据的？

```java
public void run() {
       //...
      while (waitForWork()) {
        receiveResponse();  //具体的处理方法
      }
      close();
      //...
}

private void receiveResponse() {
      if (shouldCloseConnection.get()) {
        return;
      }
      touch();
      try {
        int id = in.readInt();                    // 阻塞读取id
        if (LOG.isDebugEnabled())
          LOG.debug(getName() + " got value #" + id);
          Call call = calls.get(id);    //在calls池中找到发送时的那个对象
        int state = in.readInt();     // 阻塞读取call对象的状态
        if (state == Status.SUCCESS.state) {
          Writable value = ReflectionUtils.newInstance(valueClass, conf);
          value.readFields(in);           // 读取数据
        //将读取到的值赋给call对象，同时唤醒Client等待线程，贴出setValue()代码
          call.setValue(value);              
          calls.remove(id);               //删除已处理的call    
        } else if (state == Status.ERROR.state) {
          //...
        } else if (state == Status.FATAL.state) {
          //...
        }
      } catch (IOException e) {
        markClosed(e);
      }
}

public synchronized void setValue(Writable value) {
      this.value = value; //将读取到的值赋给call对象
      callComplete();   //具体实现,唤醒client等待线程。
}
protected synchronized void callComplete() {
      this.done = true;
      notify();         // 唤醒client等待线程
    }
```

完成的功能主要是：启动一个处理线程，读取从服务端传来的call对象，将call对象读取完毕后，唤醒client处理线程。
就这样，客户端就获取了服务端返回的数据。

