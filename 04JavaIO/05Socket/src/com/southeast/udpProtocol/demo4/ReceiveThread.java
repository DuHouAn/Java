package com.southeast.udpProtocol.demo4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by DHA on 2018/9/4.
 */
public class ReceiveThread implements Runnable{
    private DatagramSocket ds;

    public ReceiveThread(DatagramSocket ds) {
        this.ds = ds;
    }

    @Override
    public void run() {
        try {
            while(true){
                byte[] buf=new byte[1024];
                DatagramPacket dp=new DatagramPacket(buf,buf.length);
                ds.receive(dp);
                //解析数据
                String ip=dp.getAddress().getHostAddress();
                String str=new String(dp.getData(),0,dp.getLength());
                System.out.println("from "+ip+" data is:"+str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
