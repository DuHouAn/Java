package com.southeast.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用FileChannel读取数据到Buffer中
 */
public class ChannelDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile=new RandomAccessFile("c.txt","rw");

        //封装控制台输出
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));

        //获取Channel,用来从缓冲区中读取数据
        FileChannel fcin=accessFile.getChannel();

        //分配缓冲区
        ByteBuffer buffer=ByteBuffer.allocateDirect(1024);
        int len=0;
        while((len=fcin.read(buffer))!=-1){//fcin.read(buffer) 将数据写入缓存区
            buffer.flip();

            //从缓冲区中获取数据
            int limit = buffer.limit();
            char[] dst = new char[limit];
            for(int i=0;i<limit;i++){
                bw.write((char)buffer.get(i));//buffer.get(i)直接从缓冲区中获取字符
                bw.flush();
            }

            buffer.clear();//清空缓存区
        }

        accessFile.close();
    }
}
