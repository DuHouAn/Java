package com.southeast.tcpProtocol.demo5;

import java.io.*;
import java.net.Socket;

/**
 *
 * 客户端文本文件，服务器输出到控制台
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket s=new Socket("192.168.1.44",9999);

        //从文件中读取
        BufferedReader br=new BufferedReader(
                new FileReader("server.txt"));

        //向通道中写数据
        BufferedWriter bw=new BufferedWriter(
                new OutputStreamWriter(s.getOutputStream()));

        String line=null;
        while((line=br.readLine())!=null){
            bw.write(line);
            bw.newLine();
            bw.flush();
        }

        br.close();
        s.close();
    }
}
