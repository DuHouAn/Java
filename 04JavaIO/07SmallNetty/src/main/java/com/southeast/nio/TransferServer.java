package com.southeast.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by 18351 on 2018/9/5.
 */
public class TransferServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssChannel=ServerSocketChannel.open();
        ServerSocket ss=ssChannel.socket();
        ss.setReuseAddress(true);//设置地址重用
        ss.bind(new InetSocketAddress("127.0.0.1",9996));//绑定地址

        //从缓冲区中读取数据
        while(true){
            SocketChannel sc=ssChannel.accept();
            sc.configureBlocking(true);// 设置阻塞，接不到就停
            System.out.println("Accepted:"+sc);

            ByteBuffer buffer=ByteBuffer.allocate(32);
            int len=0;
            while((len=sc.read(buffer))!=-1){//将channel中数据读取到buffer中
                byte[] bys=buffer.array(); //将缓冲区中数据转化成字节数组
                String str=new String(bys,0,len);
                System.out.print(str);
            }
            buffer.clear();
        }
    }
}
