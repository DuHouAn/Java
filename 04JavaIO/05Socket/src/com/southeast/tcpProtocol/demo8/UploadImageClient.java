package com.southeast.tcpProtocol.demo8;

import java.io.*;
import java.net.Socket;

/**
 * 通过网络上传图片，并给出反馈信息
 *
 * 图片-->使用字节流
 * 反馈信息-->使用字符流
 */
public class UploadImageClient {
    public static void main(String[] args) throws IOException {
        Socket s=new Socket("192.168.1.44",3333);

        //从文件中读取数据
        BufferedInputStream bis=new BufferedInputStream(
                new FileInputStream("林青霞.jpg"));

        //向通道中写数据
        BufferedOutputStream bos=new BufferedOutputStream(s.getOutputStream());

        byte[] bys=new byte[1024];
        int len=0;
        while((len=bis.read(bys))!=-1){
            bos.write(bys,0,len);
        }

        s.shutdownOutput();

        //从通道中读取反馈信息
        BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
        System.out.println(br.readLine());

        bis.close();
        s.close();
    }
}
