package com.southeast.udpProtocol.demo1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 网络编程三要素：
 * （1）IP地址
 * （2）端口
 * （3）协议
 *
 * UDP协议接收数据：
 * A:创建UDP接收端的Socket对象
 * B:创建数据包用于接收数据
 * C:接收数据
 * D:解析数据包
 * E:释放资源
 *
 */
public class Server {
    public static void main(String[] args) throws IOException {
        //创建接收端Socket对象  new DatagramSocket()
        DatagramSocket ds = new DatagramSocket(10086);

        // 创建一个数据包(接收容器)
        // DatagramPacket(byte[] buf, int length)
        byte[] buf=new byte[1024];
        int length=buf.length;
        DatagramPacket dp=new DatagramPacket(buf,length);

        // 调用Socket对象的接收方法接收数据
        // public void receive(DatagramPacket p)
        ds.receive(dp); // 阻塞式

        //解析数据
        //获取对方IP
        /*InetAddress address=dp.getAddress();
        String ip=address.getHostAddress();*/
        String ip=dp.getAddress().getHostAddress();

        // public byte[] getData():获取数据缓冲区
        // public int getLength():获取数据的实际长度
        byte[] buf2 = dp.getData();
        int length2 = dp.getLength();
        String s = new String(buf2, 0, length2);
        System.out.println(ip + "传递的数据是:" + s);

        ds.close();
    }
}
