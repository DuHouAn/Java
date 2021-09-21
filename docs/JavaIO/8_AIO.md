# AIO

AIO（Asynchronous I/O）即异步输入/输出库是在 JDK 1.7 中引入的。虽然 NIO 在网络操作中，提供了非阻塞的方法，但是 NIO 的 IO 行为还是同步的。

对于 NIO 来说，我们的业务线程是在 IO 操作准备好时，得到通知，接着就由这个线程自行进行IO操作，IO操作本身是同步的。但是对AIO来说，则更加进了一步，它不是在 IO 准备好时再通知线程，而是在 IO 操作已经完成后，再给线程发出通知。因此 AIO 是不会阻塞的，此时我们的业务逻辑将变成一个**回调函数**，等待 IO 操作完成后，由系统自动触发。

## AsynchronousServerSocketChannel

在 AIO Socket 编程中，服务端通道是 AsynchronousServerSocketChannel，该类主要有如下方法：

- 提供了一个 open() 静态工厂
- bind() 方法用于绑定服务端 IP 地址 + 端口号
- accept() 用于接收用户连接请求

## AsynchronousSocketChannel

在客户端使用的通道是 AsynchronousSocketChannel，这个通道处理除了提供 open 静态工厂方法外，还提供了read() 和 write() 方法。

##  CompletionHandler<V,A>

在AIO编程中，发出一个事件（accept read write等）之后要指定事件处理类（回调函数），AIO 中的事件处理类是 CompletionHandler<V,A>，这个接口定义了如下两个方法，分别在异步操作成功和失败时被回调：

```java
void completed(V result,A attachment);
void failed(Throwable exc,A attachment);
```

## 示例

### 服务端

```java
package com.southeast.cn;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AIOEchoServer {

    public final static int PORT = 8001;
    public final static String IP = "127.0.0.1";

    private AsynchronousServerSocketChannel server = null;

    public AIOEchoServer(){
        try {
            //同样是利用工厂方法产生一个通道，异步通道 AsynchronousServerSocketChannel
            server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(IP,PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //使用这个通道(server)来进行客户端的接收和处理
    public void start(){
        System.out.println("Server listen on "+PORT);

        //注册事件和事件完成后的处理器，这个CompletionHandler就是事件完成后的处理器
        server.accept(null,new CompletionHandler<AsynchronousSocketChannel,Object>(){

            final ByteBuffer buffer = ByteBuffer.allocate(1024);

            // 回调函数
            @Override
            public void completed(AsynchronousSocketChannel result,Object attachment) {

                System.out.println(Thread.currentThread().getName());
                Future<Integer> writeResult = null;

                try{
                    buffer.clear();
                    result.read(buffer).get(100,TimeUnit.SECONDS);

                    System.out.println("In server: "+ new String(buffer.array()));

                    //将数据写回客户端
                    buffer.flip();
                    writeResult = result.write(buffer);
                }catch(InterruptedException | ExecutionException | TimeoutException e){
                    e.printStackTrace();
                }finally{
                    server.accept(null,this);
                    try {
                        writeResult.get();
                        result.close();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            // 回调函数
            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("failed:"+exc);
            }

        });
    }

    public static void main(String[] args) {
        new AIOEchoServer().start();
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
```



### 客户端

```java
package com.southeast.cn;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AIOClient {

    public static void main(String[] args) throws IOException {

        final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();

        InetSocketAddress serverAddress = new InetSocketAddress("127.0.0.1",8001);

        CompletionHandler<Void, ? super Object> handler = new CompletionHandler<Void,Object>(){

            @Override
            public void completed(Void result, Object attachment) {
                client.write(ByteBuffer.wrap("Hello".getBytes()),null,
                        new CompletionHandler<Integer,Object>(){

                            @Override
                            public void completed(Integer result,
                                                  Object attachment) {
                                final ByteBuffer buffer = ByteBuffer.allocate(1024);
                                client.read(buffer,buffer,new CompletionHandler<Integer,ByteBuffer>(){

                                    @Override
                                    public void completed(Integer result,
                                                          ByteBuffer attachment) {
                                        buffer.flip();
                                        System.out.println(new String(buffer.array()));
                                        try {
                                            client.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void failed(Throwable exc,
                                                       ByteBuffer attachment) {
                                    }

                                });
                            }

                            @Override
                            public void failed(Throwable exc, Object attachment) {
                            }

                        });
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
            }

        };

        client.connect(serverAddress, null, handler);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
```



