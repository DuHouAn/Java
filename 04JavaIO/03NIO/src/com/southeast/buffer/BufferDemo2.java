package com.southeast.buffer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Buffer常见方法：
 * A: Buffer分配
 *          ByteBuffer buf = ByteBuffer.allocate(48); //分配48字节
 *          CharBuffer buf = CharBuffer.allocate(1024); //分配1024字符
 * B:向Buffer中写数据
 *          (1)从Channel中写到Buffer中
 *          (2)通过Buffer的put()方式将数据写入Buffer中
 *
 * C.flip()方法：
 *      flip方法将Buffer从写模式切换到读模式。
 *      调用flip()方法会将position设回0，并将limit设置成之前position的值。
 */
public class BufferDemo2 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile=new RandomAccessFile("c.txt","rw");

        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));

        //获取Channel,用来从缓冲区中读取数据
        FileChannel fcin=accessFile.getChannel();

        //分配缓冲区
        ByteBuffer buffer=ByteBuffer.allocateDirect(1024);
        int bys=0;
        while((bys=fcin.read(buffer))!=-1){//fcin.read(buffer) 通过Channel将数据写入缓存区
            buffer.put("I am a Chinese".getBytes());//向buffer缓存区中写入数据
            buffer.flip();
            //从缓冲区中获取数据
            while(buffer.hasRemaining()){
                bw.write((char)buffer.get());//buffer.get(i)直接从缓冲区中获取字符
                bw.flush();
            }
            buffer.clear();//清空缓存区,准备再次写入
        }

        accessFile.close();
    }
}
