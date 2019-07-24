package code_04_network.tcp3;

import java.io.*;
import java.net.Socket;

/**
 * Created by 18351 on 2019/1/5.
 */
public class ClientDemo3 {
    public static void main(String[] args) throws IOException {
        //1. 创建发送端的Socket对象:这一步如果成功，就说明连接已经建立成功了。
        Socket socket=new Socket("LAPTOP-D9966H06",8888);

        //键盘录入数据
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        //2. 获取输出流，写数据
        //对输出流进行包装
        OutputStream outputStream=socket.getOutputStream();
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(outputStream));

        String line=null;

        while(true){
            line=br.readLine();
            if("-1".equals(line)){
                break;
            }
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
        //3. 释放资源
        socket.close();
    }
}
