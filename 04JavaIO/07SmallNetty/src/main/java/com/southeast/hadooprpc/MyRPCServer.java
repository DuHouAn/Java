package com.southeast.hadooprpc;

import com.southeast.hadooprpc.protocol.MyProtocol;
import com.southeast.hadooprpc.protocol.MyProtocolImpl;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.Server;

import java.io.IOException;

/**
 *当一个调用信息到达，服务器获得进程参数，计算结果，发送答复信息，然后等待下一个调用信息，
 */
public class MyRPCServer {
    public static void main(String[] args) throws IOException {
        Builder builder = new RPC.Builder(new Configuration());
        builder.setBindAddress("127.0.0.1");
        builder.setPort(5555);
        builder.setProtocol(MyProtocol.class);
        builder.setInstance(new MyProtocolImpl());

        Server server = builder.build();
        server.start();
    }
}
