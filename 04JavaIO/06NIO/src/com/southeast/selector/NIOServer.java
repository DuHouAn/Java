package com.southeast.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * 单线程管理多个连接
 *
 *  Java NIO的选择器允许一个单独的线程来监视多个输入通道，
 *  你可以注册多个通道使用一个选择器，然后使用一个单独的线程来“选择”通道：
 *  这些通道里已经有可以处理的输入，或者选择已准备写入的通道。
 *  这种选择机制，使得一个单独的线程很容易来管理多个通道。
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //1.创建选择器
        Selector selector=Selector.open();

        //2.将通道注册到选择器上
        ServerSocketChannel ssChannel=ServerSocketChannel.open();
        ssChannel.configureBlocking(false);//将通道设置为非阻塞的
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);

        //服务端
        ServerSocket ss=ssChannel.socket();
        InetSocketAddress address=new InetSocketAddress("127.0.0.1",1111);
        ss.bind(address);

        while(true){ //5.事件循环
            //3.监听事件
            selector.select();

            //4.获取到达的事件
            Set<SelectionKey> set=selector.selectedKeys();
            for(SelectionKey key:set){
                if (key.isAcceptable()) {
                    ServerSocketChannel ssChannel1 = (ServerSocketChannel) key.channel();
                    //TODO:服务器会为每个新连接创建一个 SocketChannel
                    SocketChannel sChannel = ssChannel1.accept();
                    sChannel.configureBlocking(false);//将通道设置为非阻塞的

                    // 这个新连接主要用于从客户端读取数据
                    sChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel sChannel = (SocketChannel) key.channel();
                    System.out.println(readDataFromSocketChannel(sChannel));
                    sChannel.close();
                }
                set.remove(key);
            }
        }
    }

    //从Channel中读取数据，实际上就是从缓存区中读取数据
    private static String readDataFromSocketChannel(SocketChannel sChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuilder sb=new StringBuilder();

        while(true){
            buffer.clear();//先清空缓存区
            int len=sChannel.read(buffer);//从读取数据到缓存区中
            if(len==-1){
                break;
            }
            //切换读
            buffer.flip();
            int limit = buffer.limit();
            char[] dst = new char[limit];
            for(int i=0;i<limit;i++){
                dst[i]=(char)buffer.get(i);//直接从缓冲区中获取数据
            }
            sb.append(dst);
            buffer.clear();
        }
        return sb.toString();
    }
}
