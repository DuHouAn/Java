package com.southeast.nio.tcpChannel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by DHA on 2018/9/5.
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);//设置ServerChannel为非阻塞模式
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));

        while(!socketChannel.finishConnect()){
            System.out.println("wait, or do something else...");
        }

        //文件读入流
        RandomAccessFile accessFile=new RandomAccessFile("a.txt","rw");
        FileChannel fc=accessFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(1024);
        int len=0;
        while((len=fc.read(buf))!=-1){
            buf.flip();
            //从缓冲区中写入数据到SocketChannel
            socketChannel.write(buf);
            buf.clear();
        }
        socketChannel.close();
    }
}
