package com.southeast.buffer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Buffer常见方法：
 * D:从Buffer中读取数据
 *    (1)从Buffer读取数据到Channel。
 *    (2)使用get()方法从Buffer中读取数据。
 * E:clear()和compact()
 *    TODO:clear()方法会清空整个缓冲区。
 *    TODO:compact()方法只会清除已经读过的数据。
 *         任何未读的数据都被移到缓冲区的起始处,新写入的数据将放到缓冲区未读数据的后面。
 */
public class BufferDemo3 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile=new RandomAccessFile("c.txt","rw");

        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));

        //获取Channel,用来从缓冲区中读取数据
        FileChannel fcin=accessFile.getChannel();
        FileChannel fcout=accessFile.getChannel();

        //分配缓冲区
        ByteBuffer buffer=ByteBuffer.allocateDirect(1024);
        int bys=0;
        while((bys=fcin.read(buffer))!=-1){//fcin.read(buffer) 通过Channel将数据写入缓存区
            buffer.put("I am a Chinese".getBytes());
            buffer.flip();

            //从缓冲区中获取数据
            /*while(buffer.hasRemaining()){
                bw.write((char)buffer.get());//buffer.get(i)直接从缓冲区中获取字符
                bw.flush();
            }*/
            fcout.write(buffer);//从缓存区中读取数据到通道中
            buffer.clear();//清空缓存区,准备再次写入
        }


        accessFile.close();
    }
}
