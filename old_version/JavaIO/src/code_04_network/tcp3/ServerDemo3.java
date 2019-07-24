package code_04_network.tcp3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 18351 on 2019/1/5.
 */
public class ServerDemo3 {
    public static void main(String[] args) throws IOException {
        //1. 创建接收端的Socket对象
        ServerSocket serverSocket=new ServerSocket(8888);

        //2. 监听客户端连接。返回一个对应的Socket对象
        Socket socket=serverSocket.accept();

        //3. 获取输入流，读取数据显示在控制台
        //这里对输入流要进行包装
        InputStream inputStream=socket.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
        String line=null;
        while((line=br.readLine())!=null){
            System.out.println(line);
        }

        //4. 释放资源
        socket.close();
    }
}