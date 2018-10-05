package com.southeast.NIOTest;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 源文件 a.txt--InputStream--FileInputStream
 *      FileChannel getChannel()  返回与此文件输入流相关联的唯一的FileChannel对象。
 * 目的地文件 b.txt--OutputStream--FileOutputStream
 *       FileChannel getChannel()  返回与此文件输入流相关联的唯一的FileChannel对象。
 *
 * FileChannel
 *      long read(ByteBuffer[] dsts)    从该通道读取到给定缓冲区的字节序列。
 *      long write(ByteBuffer[] srcs)   从给定的缓冲区向该通道写入一系列字节。
 */
public class CopyFile {
    public static void main(String[] args) throws IOException {
        FileInputStream fis=new FileInputStream("a.txt");
        FileOutputStream fos=new FileOutputStream("b.txt");

        //获取输入字节流的文件通道
        FileChannel fcin = fis.getChannel();
        //获取输出字节流的文件通道
        FileChannel fcout=fos.getChannel();

        //为缓冲区分配 1024 个字节
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
       /* while(true){
            //从输入通道中读取数据到缓冲区中
            int r = fcin.read(buffer);
            if(r==-1){ //r==-1表示读到文件末尾
                break;
            }
            //TODO:切换读写
            buffer.flip();//缓存区由写切换到读
            fcout.write(buffer);
            buffer.clear();
        }*/
       int by=0;
        while((by=fcin.read(buffer))!=-1){
            //TODO:切换读写
            buffer.flip();//缓存区由写切换到读
            fcout.write(buffer);
            buffer.clear();
        }

        fis.close();
        fos.close();
    }
}
