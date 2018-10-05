package com.southeast.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *Java NIO中的FileChannel是一个连接到文件的通道。可以通过文件通道读写文件。
 *  FileChannel无法设置为非阻塞模式，它总是运行在阻塞模式下。
 *
 *  使用FileChannel
 *      A:打开FileChannel
 *          我们无法直接打开一个FileChannel，需要通过使用
 *          一个InputStream、OutputStream或RandomAccessFile来获取一个FileChannel实例。
 *      B:从FileChannel中读取数据
 *      C:向FileChannel中写入数据
 *      D:关闭FileChannel
 */
public class FileChannelDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile=new RandomAccessFile("a.txt","rw");

        FileChannel fc=accessFile.getChannel();

        ByteBuffer buffer=ByteBuffer.allocate(128);

        //从FileChannel中读取数据
        int len=0;
        while((len=fc.read(buffer))!=-1){
            int limit=buffer.limit();
            for(int i=0;i<limit;i++){
                System.out.print((char)buffer.get(i));
            }
            buffer.clear();
        }
        buffer.flip();

        //向FileChannel中写入数据
        String newData = "New String to write to file..." + System.currentTimeMillis();
        buffer.clear();
        buffer.put(newData.getBytes()); //先将数据放入缓存区

        while(buffer.hasRemaining()){
            fc.write(buffer);//从缓存区中写入数据到通道中
            //在while循环中调用的。因为无法保证write()方法一次能向FileChannel写入多少字节，
            //因此需要重复调用write()方法，直到Buffer中已经没有尚未写入通道的字节。
        }

        fc.close();
        accessFile.close();
    }
}
