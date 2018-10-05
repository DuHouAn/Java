package com.southeast.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 *
 * Java NIO中的SocketChannel是一个连接到==TCP网络套接字==的通道。
 *
 *  SocketChannel设置为非阻塞模式
 *      可以设置 SocketChannel 为非阻塞模式（non-blocking mode）.
 *      设置之后，就可以在异步模式下调用connect(), read() 和write()了。
 *
 * 非阻塞模式与选择器
 *      非阻塞模式与选择器搭配会工作的更好，通过将一或多个SocketChannel注册到Selector，
 *      可以询问选择器哪个通道已经准备好了读取，写入等.
 */
public class SocketChannelDemo2 {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);//设置ServerChannel为非阻塞模式
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

        //如果SocketChannel在非阻塞模式下，此时调用connect()，
        //该方法可能在连接建立之前就返回了。为了确定连接是否建立，可以调用finishConnect()的方法。
        while(!socketChannel.finishConnect()){
            System.out.println("wait, or do something else...");
        }

        //写入数据到 SocketChannel
        String newData = "New String to write to file..." + System.currentTimeMillis();

        //生成Buffer，并向Buffer中写数据
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());

        //切换buffer为读模式
        buf.flip();
        while(buf.hasRemaining()) {
            socketChannel.write(buf);
            //SocketChannel.write()方法的调用是在一个while循环中的。
            // write()方法无法保证能写多少字节到SocketChannel。
            // 所以，我们重复调用write()直到Buffer没有要写的字节为止。
        }

        socketChannel.close();
    }
}
