package com.southeast.tcpProtocol.demo10;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 通过网络传输图片
 * 通过while循环可以改进一个服务器接收多个客户端。
 *
 * 但是有缺点：程序是顺序执行的
 *  用户一：上传一个很大的文件，很耗时间
 *  用户二：只能等用户一上传完才能上传文件
 *  -->利用线程改进
 */
public class UploadImageServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss=new ServerSocket(5555);
        while(true){
            Socket s=ss.accept();
            new UserThread(s).start();
        }
    }
}
