package com.southeast.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by DHA on 2018/9/6.
 */
public class TimeNIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel=SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1",12345));

        if(!socketChannel.finishConnect()){
            System.out.println("wait,or dosomething");
        }

        FileInputStream fis=new FileInputStream("a.txt");
        FileChannel fileChannel=fis.getChannel();

        //从 fileChannel向 socketChannel传输
        long pos=0;
        long count=fileChannel.size();
        fileChannel.transferTo(pos,count,socketChannel);

        fileChannel.close();
        fis.close();
        socketChannel.close();
    }
}
