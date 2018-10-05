package com.southeast.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by DHA on 2018/9/5.
 */
public class TraditionalServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=new ServerSocket(9999);

        while(true){
            Socket socket=serverSocket.accept();

            //获取输入流
            BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line=null;
            while((line=br.readLine())!=null){
                System.out.println("接收到客户端"+line+"的心跳，正常连接………………");
            }
        }
    }
}
