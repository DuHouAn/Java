package code_04_network.udp;

import java.io.IOException;
import java.net.*;

/**
 * Created by 18351 on 2019/1/5.
 */
public class SendDemo {
    public static void main(String[] args) throws IOException {
        //1. 创建发送端Socket对象
        DatagramSocket ds=new DatagramSocket();

        //2. 创建数据，并把数据打包
        byte[] bys="hello".getBytes();
        // 长度
        int length = bys.length;
        // IP地址对象
        InetAddress address = InetAddress.getByName("LAPTOP-D9966H06");
        // 端口
        int port = 10086;
        DatagramPacket dp = new DatagramPacket(bys, length, address, port);

        //3. 调用Socket对象的发送方法发送数据包
        ds.send(dp);

        //4. 释放资源
        ds.close();
    }
}
