package code_04_network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by 18351 on 2019/1/5.
 */
public class ReceiveDemo {
    public static void main(String[] args) throws IOException {
        //1. 创建接收端Socket对象
        DatagramSocket ds=new DatagramSocket(10086);

        //2. 创建一个数据包(接收容器)
        byte[] bys=new byte[1024];
        int length=bys.length;
        DatagramPacket dp=new DatagramPacket(bys,length);

        //3. 调用Socket对象的接收方法接收数据
        //public void receive(DatagramPacket p)
        ds.receive(dp);//阻塞式

        //4. 解析数据包，并显示在控制台
        InetAddress inetAddress=dp.getAddress();
        String ip=inetAddress.getHostAddress();
        // public byte[] getData():获取数据缓冲区
        // public int getLength():获取数据的实际长度
        byte[] bys2 = dp.getData();
        int len = dp.getLength();
        String s = new String(bys2, 0, len);
        System.out.println(ip + "传递的数据是:" + s);

        //5. 释放资源
        ds.close();
    }
}
