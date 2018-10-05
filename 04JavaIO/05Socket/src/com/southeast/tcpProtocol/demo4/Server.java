package com.southeast.tcpProtocol.demo4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 客户端键盘录入，
 * 服务器输出文本文件
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(8889);

        Socket s=ss.accept();

        BufferedReader br=new BufferedReader(
                new InputStreamReader(s.getInputStream()));
        BufferedWriter bw=new BufferedWriter(
                new FileWriter("server.txt"));
        String line=null;
        while((line=br.readLine())!=null){
            bw.write(line);
            bw.newLine();
            bw.flush();
        }

        bw.close();
        s.close();
    }
}
