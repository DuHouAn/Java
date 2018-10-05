package com.southeast.nio.tcpSocket;

import java.io.*;
import java.net.Socket;

/**
 * 通过网络传输文件（未引入 ServerSocketChannel和SocketChannel）
 *
 * 问题：
 * 读取文本文件是可以以null作为结束信息的，但是呢，通道内是不能这样结束信息的。
 * 所以，服务器根本就不知道你结束了。而你还想服务器给你反馈，就会相互等待了。
 *
 * 解决：
 * 方式一：再多写一条数据，告诉服务器，读取到这条数据说明我就结束，你也结束吧。
 * 		这样做可以解决问题，但是不好。
 * 方式二：Socket对象提供了一种解决方案
 * 		public void shutdownOutput()
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket s=new Socket("192.168.1.44",2222);

        //从文件中读取数据
        BufferedReader br=new BufferedReader(
                new FileReader("a.txt"));

        //向通道中写数据
        BufferedWriter bw=new BufferedWriter(
                new OutputStreamWriter(s.getOutputStream()));

        String line=null;
        while((line=br.readLine())!=null){
            bw.write(line);
            bw.newLine();
            bw.flush();
        }
        //方式一：再多写一条数据，告诉服务器，读取到这条数据说明我就结束，你也结束吧。
        /*bw.write("over");
        bw.newLine();
        bw.flush();//注意：这里字符要刷新*/

        //方式二：Socket对象提供了一种解决方案 shutdownOutput()
        s.shutdownOutput();

        //从通道中读取反馈信息
        BufferedReader br2=new BufferedReader(new InputStreamReader(s.getInputStream()));
        System.out.println(br2.readLine());

        br.close();
        s.close();
    }
}
