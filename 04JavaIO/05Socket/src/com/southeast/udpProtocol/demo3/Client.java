package com.southeast.udpProtocol.demo3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 *  数据来自于键盘录入
 * 键盘录入数据要自己控制录入结束。
 *  属输入 -1表示录入结束
 */
public class Client {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds=new DatagramSocket();

        //封装录入数据
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        String line=null;
        while(true){
            line=br.readLine();
            if("-1".equals(line)){
                break;
            }
            byte[] buf=line.getBytes();
            DatagramPacket dp=new DatagramPacket(buf,buf.length,
                    InetAddress.getByName("192.168.1.44"),54321);
            ds.send(dp);
        }

        br.close();
        ds.close();
    }
}
