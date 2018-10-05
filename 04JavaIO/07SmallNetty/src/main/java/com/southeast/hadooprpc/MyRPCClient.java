package com.southeast.hadooprpc;

import com.southeast.hadooprpc.protocol.MyProtocol;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * 首先，
 *  客户机调用进程发送一个有进程参数的调用信息到服务进程，然后等待应答信息。
 *  在服务器端，进程保持睡眠状态直到调用信息到达为止。
 */
public class MyRPCClient {
    public static void main(String[] args) throws IOException {
        MyProtocol protocol = RPC.getProxy(
                MyProtocol.class, Long.MAX_VALUE, new InetSocketAddress(
                        "127.0.0.1", 5555), new Configuration());
        //客户端调用进程接收答复信息，获得进程结果，然后调用执行继续进行。
        String ret = protocol.heartBeat("wilson");
        System.out.println(ret);
    }
}
