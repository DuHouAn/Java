package com.southeast.tcpProtocol.demo10;

import java.io.*;
import java.net.Socket;

/**
 * Created by DHA on 2018/9/4.
 */
public class UserThread extends Thread{
    private Socket s;

    public UserThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try{
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
            s.close(); //因为每次传输的数据都不相同
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
