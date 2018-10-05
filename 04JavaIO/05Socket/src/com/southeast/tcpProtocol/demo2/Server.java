package com.southeast.tcpProtocol.demo2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by DHA on 2018/9/4.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(12307);

        Socket s=ss.accept();//阻塞

        InputStream is=s.getInputStream();
        byte[] bys=new byte[1024];
        int len=is.read(bys);//阻塞
        String str=new String(bys,0,len);
        String ip=s.getInetAddress().getHostAddress();
        System.out.println("from:"+ip+" data is:"+str);

        // 获取输出流-->服务器向客户端发送反馈信息
        OutputStream os = s.getOutputStream();
        os.write("数据已经收到".getBytes());

        s.close();
    }
}
