package code_04_network.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by 18351 on 2019/1/5.
 */
public class ClientDemo {
    public static void main(String[] args) throws IOException {
        //1. 创建发送端的Socket对象:这一步如果成功，就说明连接已经建立成功了。
        Socket socket=new Socket("LAPTOP-D9966H06",8888);

        //2. 获取输出流，写数据
        OutputStream outputStream=socket.getOutputStream();
        outputStream.write("hello".getBytes());

        //3. 释放资源
        socket.close();
    }
}
