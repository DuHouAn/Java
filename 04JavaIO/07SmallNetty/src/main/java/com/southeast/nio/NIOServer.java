package com.southeast.nio;

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
 * Created by DHA on 2018/9/5.
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc=ServerSocketChannel.open();
        ServerSocket socket=ssc.socket();
        socket.bind(new InetSocketAddress(1111));
        ssc.configureBlocking(false);

        while(true){
            SocketChannel sc= ssc.accept();
            if(sc!=null){
                sc.configureBlocking(false);
                //从通道中读取数据到 buffer中
                ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
                int len=0;
                while((len=sc.read(byteBuffer))!=-1){
                    byte[] bys=byteBuffer.array();
                    byteBuffer.clear();
                    String str=new String(bys,0,len);
                    System.out.println(str);
                }
            }
        }


    }
}
