package com.southeast.tcpProtocol.demo2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * java.net.ConnectException: Connection refused: connect
 * 因为没有服务端
 * 必须要先建立连接
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket s=new Socket("192.168.1.44",12307);

        //获取输入流
        OutputStream os=s.getOutputStream();
        os.write("发送数据".getBytes());

        //获取输出流
        InputStream is=s.getInputStream();
        byte[] bys=new byte[1024];
        int len=is.read(bys);//阻塞
        System.out.println("client:"+new String(bys,0,len));

        s.close();
    }
}
