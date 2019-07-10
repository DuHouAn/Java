package code_04_network.udp2;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 数据来自于键盘录入
 * 键盘录入数据要自己控制录入结束。
 */
public class SendDemo2 {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds=new DatagramSocket();

        //封装键盘录入数据
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String line=null;

        while(true){
            line=br.readLine();
            if("-1".equals(line)){
                break;
            }
            byte[] bys=line.getBytes();
            DatagramPacket dp=
                    new DatagramPacket(bys,bys.length,InetAddress.getByName("LAPTOP-D9966H06"),12345);
            ds.send(dp);
        }

        br.close();
        //4. 释放资源
        ds.close();
    }
}
