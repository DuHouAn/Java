package com.southeast.tcpProtocol.demo9;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 通过网络传输图片
 * 通过while循环可以改进一个服务器接收多个客户端。
 *
 * 但是有缺点：程序是顺序执行的
 *  用户一：上传一个很大的文件，很耗时间
 *  用户二：只能等用户一上传完才能上传文件
 *  -->利用线程改进
 */
public class UploadImageServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(4444);

        while(true){
            Socket s=ss.accept();
            //获取通道内数据
            BufferedInputStream bis=new BufferedInputStream(s.getInputStream());

            //文件写出流
            BufferedOutputStream bos=new BufferedOutputStream(
                    new FileOutputStream("Copy.jpg"));

            byte[] bys=new byte[1024];
            int len=0;
            while((len=bis.read(bys))!=-1){
                bos.write(bys,0,len);
            }

            //向客户端发送反馈消息
            BufferedWriter bw=new BufferedWriter(
                    new OutputStreamWriter(s.getOutputStream()));
            bw.write("图片上传成功");
            bw.newLine();
            bw.flush();

            bos.close();
            s.close();
        }
    }
}
