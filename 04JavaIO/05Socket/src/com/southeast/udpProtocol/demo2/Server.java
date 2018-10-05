package com.southeast.udpProtocol.demo2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 多次启动接收端(服务器端)：
 * 		java.net.BindException: Address already in use: Cannot bind
 * 		端口被占用。
 * 	(注意服务端不能多次启动，因为端口已经被占用了，但是客户端可以多次移动)
 *
 * 通过UDP协议接收数据：
    A:创建UDP接收端的Socket对象   new DatagramSocket(int port);
    B:创建数据包用于接收数据   DatagramPacket dp=new DatagramPacket(buf,length);
    C:接收数据    ds.receive(dp); // 阻塞式
    D:解析数据包   byte[] dp.getData(); 、int dp.getLength()
    E:释放资源 close()
 */
public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds=new DatagramSocket(12345);

        byte[] buf=new byte[1024];
        DatagramPacket dp=new DatagramPacket(buf,buf.length);

        //接收数据，阻塞式
        ds.receive(dp);

        String ip=dp.getAddress().getHostAddress();
        String str=new String(dp.getData(),0,dp.getLength());
        System.out.println("from " + ip + " data is : " + str);

        ds.close();
    }
}
