package com.southeast.tcpProtocol.demo6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 通过网络传输文件（简单版）
 */
public class UploadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(1111);

        Socket s=ss.accept();

        //获取通道内数据
        BufferedReader br=new BufferedReader(
                new InputStreamReader(s.getInputStream()));
        //文件写出流
        BufferedWriter bw=new BufferedWriter(
                new FileWriter("Copy.txt"));

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
