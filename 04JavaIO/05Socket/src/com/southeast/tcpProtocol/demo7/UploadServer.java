package com.southeast.tcpProtocol.demo7;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 通过网络传输文件（deno6的升级版）
 */
public class UploadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(2222);

        Socket s=ss.accept();

        //获取通道内数据
        BufferedReader br=new BufferedReader(
                new InputStreamReader(s.getInputStream()));
        //文件写出流
        BufferedWriter bw=new BufferedWriter(
                new FileWriter("Copy.txt"));

        String line=null;
        while((line=br.readLine())!=null){
            //方式一：服务器读到结束数据，就停止读取数据了。
           /* if("over".equals(line)){
                break;
            }*/
           //方式二：不需要改写服务端代码
            bw.write(line);
            bw.newLine();
            bw.flush();
        }

        //向客户端发送反馈消息
        BufferedWriter bw2=new BufferedWriter(
                new OutputStreamWriter(s.getOutputStream()));
        bw2.write("文件上传成功");
        bw2.newLine();
        bw2.flush();

        bw.close();
        s.close();
    }
}
