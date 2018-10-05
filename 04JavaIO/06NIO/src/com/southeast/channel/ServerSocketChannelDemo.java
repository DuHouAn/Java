package com.southeast.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Java NIO中的 ServerSocketChannel 是一个可以
 *  监听新进来的TCP连接的通道,就像标准IO中的ServerSocket一样。
 */
public class ServerSocketChannelDemo {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(9999));

        while(true) {
            SocketChannel socketChannel =
                    serverSocketChannel.accept();
            //通过 ServerSocketChannel.accept() 方法监听新进来的连接。当 accept()方法返回的时候,
            // 它返回一个包含新进来的连接的 SocketChannel。accpet()方法是一个阻塞的方法

        }

        //serverSocketChannel.close();
    }
}
