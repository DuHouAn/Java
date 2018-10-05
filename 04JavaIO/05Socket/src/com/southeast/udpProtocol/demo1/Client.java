package com.southeast.udpProtocol.demo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 网络编程三要素：
 * （1）IP地址
 * （2）端口
 * （3）协议
 *
 * UDP协议发送数据：
 * A:创建发送端Socket对象  new DatagramSocket()
 * B:创建数据，并把数据打包
 * C:调用Socket对象的发送方法发送数据包
 * D:释放资源
 */
public class Client {
    public static void main(String[] args) throws IOException {
        //创建发送端Socket对象  new DatagramSocket()
        DatagramSocket ds = new DatagramSocket();

        // 创建数据，并把数据打包
        // DatagramPacket(byte[] buf, int length, InetAddress address, int port)
        byte[] buf="我来了".getBytes(); //注意传输的数据不会太大
        int length=buf.length;
        InetAddress address=InetAddress.getByName("192.168.211.1");
        int port=10086;
        DatagramPacket dp=new DatagramPacket(buf,length,address,port);

        // 调用Socket对象的发送方法发送数据包
        // public void send(DatagramPacket p)
        ds.send(dp);

        ds.close();
    }
}
