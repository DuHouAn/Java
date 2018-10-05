package com.southeast.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by 18351 on 2018/9/5.
 */
public class TransferClient {
    public static void main(String[] args) throws IOException {
        //打开Socket的NIO管道
        SocketChannel sc=SocketChannel.open(); //TODO: SocketChannel 通道
        sc.configureBlocking(true);//设置为阻塞
        sc.connect(new InetSocketAddress("127.0.0.1",9996));

        if(!sc.finishConnect()){ //如果没有建立连接，则等待或者做其他事情
            System.out.println("wait or do something...");
        }

        FileInputStream fis=new FileInputStream("a.txt");
        FileChannel fc=fis.getChannel(); //TODO: FileChannel 通道

      /*  long size=fc.size();
        int pos=0;
        int offset=1024;
        long curnset=0; //
        long counts=0;//传输字节数

        while(pos<size){
            curnset=fc.transferTo(pos,1024,sc); //将 fc Channel中数据直接传到 sc Channel中
            pos+=offset;
            counts+=curnset;
        }*/

        long pos=0;
        long count=fc.size();
        fc.transferTo(pos,count,sc);

        fc.close();
        System.out.println("传输的字节数"+count);

        fis.close();
        sc.close();
    }
}
