package com.southeast.buffer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 缓冲区本质上是一块可以写入数据，然后可以从中读取数据的内存。
 * 这块内存被包装成NIO Buffer对象，并提供了一组方法，用来方便的访问该块内存。
 *
 * 使用BUffer读写数据：
 *  A:写入数据到Buffer
 *  B:调用flip()方法 //flip()方法将Buffer从写模式切换到读模式
 *  C:从Buffer中读取数据
 *  D:调用clear()方法或者compact()方法 //一旦读完了所有的数据，就需要清空缓冲区，让它可以再次被写入。
 *      clear()方法会清空整个缓冲区。
 *      compact()方法只会清除已经读过的数据。任何未读的数据都被移到缓冲区的起始处，
 *          新写入的数据将放到缓冲区未读数据的后面。
 */
public class BufferDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile=new RandomAccessFile("c.txt","rw");

        //封装控制台输出
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));

        //获取Channel,用来从缓冲区中读取数据
        FileChannel fcin=accessFile.getChannel();

        //分配缓冲区
        ByteBuffer buffer=ByteBuffer.allocateDirect(1024);
        int bys=0;
        while((bys=fcin.read(buffer))!=-1){//fcin.read(buffer) 将数据写入缓存区
            buffer.flip();
            //从缓冲区中获取数据
            while(buffer.hasRemaining()){
                bw.write((char)buffer.get());//buffer.get(i)直接从缓冲区中获取字符
                bw.flush();
            }
            buffer.clear();//清空缓存区
        }

        accessFile.close();
    }
}
