package com.southeast.tcpProtocol.demo3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  客户端键盘录入，
 *  服务器输出到控制台
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(8888);

        Socket s=ss.accept();

        BufferedReader br=new BufferedReader(
                new InputStreamReader(s.getInputStream()));
        String line=null;
        while((line=br.readLine())!=null){
            System.out.println(line);
        }
        s.close();
    }
}
