package com.southeast.tcpProtocol.demo1;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * * TCP协议接收数据：
 * A:创建接收端的Socket对象 ServerSocket(int port)
 * B:监听客户端连接。返回一个对应的Socket对象
 * C:获取输入流，读取数据显示在控制台
 * D:释放资源
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket socket=new ServerSocket(8888);

        //监听客户端连接。返回一个对应的Socket对象
        // public Socket accept()
        Socket s=socket.accept();

        InputStream is=s.getInputStream();

        byte[] bys=new byte[1024];
        int len=is.read(bys);//阻塞式方法
        String ss=new String(bys,0,len);

        String ip = s.getInetAddress().getHostAddress();
        System.out.println(ip+"==="+ss);

        s.close();
    }
}
