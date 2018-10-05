package com.southeast.selector;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by 18351 on 2018/9/4.
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 1111);
        OutputStream out = socket.getOutputStream();
        String s = "hello world";
        out.write(s.getBytes());
        out.close();
    }
}
