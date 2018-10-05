package com.southeast.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 *
 * Java NIO中的SocketChannel是一个连接到==TCP网络套接字==的通道。可以通过以下2种方式创建SocketChannel：
 *      方式一：打开一个SocketChannel并连接到互联网上的某台服务器。
 *      方式二： 一个新连接到达ServerSocketChannel时，会创建一个SocketChannel。
 */
public class SocketChannelDemo {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

        //写入数据到 SocketChanne
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
