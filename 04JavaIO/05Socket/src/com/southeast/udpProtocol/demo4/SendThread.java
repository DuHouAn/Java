package com.southeast.udpProtocol.demo4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 发送数据线程
 */
public class SendThread implements Runnable{
    private DatagramSocket ds;

    public SendThread(DatagramSocket ds) {
        this.ds = ds;
    }

    @Override
    public void run() {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        String line=null;
        try {
            while((line=br.readLine())!=null){
                byte[] buf=line.getBytes();
                DatagramPacket dp=new DatagramPacket(buf,buf.length,
                        InetAddress.getByName("192.168.1.44"),12306);
                ds.send(dp);
            }
            ds.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
