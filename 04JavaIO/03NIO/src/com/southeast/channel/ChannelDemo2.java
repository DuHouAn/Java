package com.southeast.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通道之间的数据传输
 *      在Java NIO中，如果两个通道中有一个是FileChannel，
 *      那你可以直接将数据从一个channel，传输到另外一个channel。
 *
 * transferFrom()
 *      FileChannel的transferFrom()方法可以将数据从源通道传输到FileChannel中
 *
 *        public abstract long transferFrom(
 *          ReadableByteChannel src,long position, long count)throws IOException;
 *      //position表示从position处开始向目标文件写入数据
 *      //count表示最多传输的字节数
 *
 *      注意：
 *      此外要注意，在SoketChannel的实现中，SocketChannel只会传输此刻准备好的数据（可能不足count字节）。
 *      因此，SocketChannel可能不会将请求的所有数据(count个字节)全部传输到FileChannel中。
 *
 * transferTo()
 *      transferTo()方法将数据从FileChannel传输到其他的channel中
 *          public abstract long transferTo(
 *              long position, long count,WritableByteChannel target)   throws IOException;
 *
 */
public class ChannelDemo2 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis=new FileInputStream("a.txt");
        FileOutputStream fos=new FileOutputStream("b.txt");

        FileChannel fcin = fis.getChannel();
        FileChannel fcout=fos.getChannel();

        long position = 0;
        long count = fcin.size();
        //fcout.transferFrom(fcin,position,count); //将数据从源通道fcin传输到fcout中
        fcin.transferTo(position,count,fcout);//将数据从源通道fcin传输到fcout中
        fis.close();
        fos.close();
    }
}
