package com.southeast.nio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by DHA on 2018/9/5.
 */
public class TraditionalClient {
    public static void main(String[] args) throws IOException{
        Socket socket=new Socket("127.0.0.1",9999);

        //获取输出流
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        bw.write("wilson");
        bw.newLine();
        bw.flush();

        socket.close();
    }
}
