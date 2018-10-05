package com.southeast.nio.tcpSelectorChannel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by DHA on 2018/9/5.
 */
public class NIOCilent {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel=SocketChannel.open();

        socketChannel.configureBlocking(false);//设置ServerChannel为非阻塞模式
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

        while(!socketChannel.finishConnect()){
            System.out.println("wait, or do something else...");
        }

        //文件读入流
        RandomAccessFile accessFile=new RandomAccessFile("a.txt","rw");
        FileChannel fc=accessFile.getChannel();

        ByteBuffer buffer=ByteBuffer.allocate(1024);
        int len=0;
        while((len=fc.read(buffer))!=-1){
            buffer.flip();
            socketChannel.write(buffer);//从缓冲区中写入数据到Channel中
        }
        socketChannel.close();
    }
}
