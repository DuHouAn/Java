package com.southeast.nio.tcpChannel;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 通过 ServerSocketChannel和SocketChannel来实现
 * 基于tcp协议的通信
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);// //ServerSocketChannel设置成非阻塞模式。

        //文件写出流
        RandomAccessFile accessFile=new RandomAccessFile("Copy.txt","rw");
        FileChannel fc=accessFile.getChannel();

        while(true){
            SocketChannel socketChannel =
                    serverSocketChannel.accept(); //SocketChannel通道

            if(socketChannel!=null){
                ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
                byteBuffer.clear();
                int len=0;
                while ((len=socketChannel.read(byteBuffer))!=-1){//从Channel中读取数据到Buffer中
                    byteBuffer.flip();//切换读为写

                    //从Buffer中写入数据到Channel中
                    fc.write(byteBuffer);
                    byteBuffer.clear();
                }
            }
        }

        //serverSocketChannel.close();
    }
}
