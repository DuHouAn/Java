package com.southeast.udpProtocol.demo4;

import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 使用线程改进：
 * 可以实现在一个窗口发送和接收数据了（类似一个聊天室的功能）
 */
public class ChatRoom {
    public static void main(String[] args) throws SocketException {
        DatagramSocket dsSender=new DatagramSocket();
        DatagramSocket dsReceive=new DatagramSocket(12306);

        SendThread st=new SendThread(dsSender);
        ReceiveThread rt=new ReceiveThread(dsReceive);

        Thread t1=new Thread(st);
        Thread t2=new Thread(rt);

        t1.start();
        t2.start();
    }
}
