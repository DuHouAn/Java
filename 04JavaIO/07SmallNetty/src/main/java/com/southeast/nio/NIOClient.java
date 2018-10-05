package com.southeast.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by DHA on 2018/9/5.
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 1111);
        OutputStream out = socket.getOutputStream();
        String s = "wilson";
        out.write(s.getBytes());
        out.close();
    }
}
