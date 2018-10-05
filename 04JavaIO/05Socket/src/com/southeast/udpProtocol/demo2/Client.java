package com.southeast.udpProtocol.demo2;

import java.io.IOException;
import java.net.*;

/**
* 客户端
 *
 *  A:创建发送端Socket对象  new DatagramSocket()
    B:创建数据，并把数据打包   DatagramPacket(byte[] buf, int length, InetAddress address, int port)
    C:调用Socket对象的发送方法发送数据包  public void send(DatagramPacket p)
    D:释放资源  close()
 */
public class Client {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds=new DatagramSocket();

        byte[] buf=("客户端发送数据"+System.currentTimeMillis()).getBytes();
        DatagramPacket dp=new DatagramPacket(buf,buf.length,
                InetAddress.getByName("192.168.1.44"),12345);

        ds.send(dp);
        ds.close();
    }
}
