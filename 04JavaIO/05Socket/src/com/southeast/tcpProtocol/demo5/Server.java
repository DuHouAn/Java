package com.southeast.tcpProtocol.demo5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by DHA on 2018/9/4.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(9999);

        Socket s=ss.accept();

        BufferedReader br=new BufferedReader(
                new InputStreamReader(s.getInputStream()));
        String line=null;
        while((line=br.readLine())!=null){
            System.out.println(line);
        }
        s.close();
    }
}
