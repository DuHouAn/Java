package com.southeast.udpProtocol.demo3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by DHA on 2018/9/4.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds=new DatagramSocket(54321);

        while(true){
            byte[] buf=new byte[1024];
            DatagramPacket dp=new DatagramPacket(buf,buf.length);

            //接收数据，阻塞式
            ds.receive(dp);

            String ip=dp.getAddress().getHostAddress();
            String str=new String(dp.getData(),0,dp.getLength());
            System.out.println("from "+ip+" data is:"+str);
        }
        // 释放资源
        // 接收端应该一直开着等待接收数据，是不需要关闭
        // ds.close();
    }
}
