package com.southeast.tcpProtocol.demo4;

import java.io.*;
import java.net.Socket;

/**
 * Created by DHA on 2018/9/4.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket s=new Socket("192.168.1.44",8889);

        //从键盘录入数据
        BufferedReader br=new BufferedReader(
                new InputStreamReader(System.in));

        //向通道中写数据
        BufferedWriter bw=new BufferedWriter(
                new OutputStreamWriter(s.getOutputStream()));

        String line=null;
        while((line=br.readLine())!=null){
            if("-1".equals(line)){
                break;
            }
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
        s.close();
    }
}
