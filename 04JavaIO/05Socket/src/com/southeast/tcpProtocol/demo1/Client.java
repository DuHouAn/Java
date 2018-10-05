package com.southeast.tcpProtocol.demo1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *  TCP协议发送数据：
 * A:创建发送端的Socket对象
 * 		这一步如果成功，就说明连接已经建立成功了。
 * B:获取输出流，写数据
 * C:释放资源
 *
 * 连接被拒绝。TCP协议一定要先开服务器。
 * java.net.ConnectException: Connection refused: connect
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("192.168.211.1", 8888);

        OutputStream os=s.getOutputStream();
        os.write("TCP我来了".getBytes());

        os.close();
    }
}
