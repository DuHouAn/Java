package com.southeast.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by DHA on 2018/9/5.
 */
public class ServerSocketChannelDemo2 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);// //ServerSocketChannel设置成非阻塞模式。
        while(true) {
            SocketChannel socketChannel =
                    serverSocketChannel.accept();


            //ServerSocketChannel可以设置成非阻塞模式。在非阻塞模式下，accept() 方法会立刻返回，
            //如果还没有新进来的连接,返回的将是null。 因此，需要检查返回的SocketChannel是否是null
            if(socketChannel!=null){
                //使用socketChannel做一些工作
            }
        }
    }
}
