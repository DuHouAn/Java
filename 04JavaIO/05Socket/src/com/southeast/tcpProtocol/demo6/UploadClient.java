package com.southeast.tcpProtocol.demo6;

import java.io.*;
import java.net.Socket;

/**
 * Created by DHA on 2018/9/4.
 */
public class UploadClient {
    public static void main(String[] args) throws IOException {
        Socket s=new Socket("192.168.1.44",1111);

        //从文件中读取数据
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
