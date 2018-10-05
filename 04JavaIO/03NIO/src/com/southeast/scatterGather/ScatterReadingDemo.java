package com.southeast.scatterGather;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Java NIO开始支持scatter/gather，
 * scatter/gather用于描述从Channel中读取或者写入到Channel的操作。
 *
 *  从Channel中分散（scatter）读取，是指在读操作时将读取的数据写入多个buffer中。
 *  因此，从Channel中读取的数据将“分散（scatter）”到多个Buffer中。
 *  聚集（gather）写入一个Channel，是指在写操作时将多个buffer的数据写入同一个Channel，因此，多个Buffer中的数据将“聚集（gather）”后写入到一个Channel。

 *  scatter/gather经常用于需要将传输的数据分开处理的场合，
 *  例如传输一个由消息头和消息体组成的消息，你可能会将消息体和消息头分散到不同的buffer中，
 *  这样你可以方便的处理消息头和消息体。
 *
 *  Scattering Reads
 *  是指数据从一个channel读取到多个buffer中
 *
 *
 */
public class ScatterReadingDemo {
    public static void main(String[] args) throws IOException {
        FileInputStream fis=new FileInputStream("a.txt");
        FileOutputStream fos=new FileOutputStream("b.txt");

        FileChannel fcin = fis.getChannel();
        FileChannel fcout=fos.getChannel();

        ByteBuffer header = ByteBuffer.allocate(64);
        ByteBuffer body   = ByteBuffer.allocate(1024);

        ByteBuffer[] bufferArray = { header, body };

        long bys=0;
        while((bys= fcin.read(bufferArray))!=-1){ //从多个Buffer中读取数据到同一个Channel
            /**
             * fcin.read(bufferArray))
             *    注意buffer首先被插入到数组，然后再将数组作为channel.read()的输入参数。
             *    read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，
             *    当一个buffer被写满后，channel紧接着向另一个buffer中写。
             */

            bufferArray[0].flip();
            bufferArray[1].flip();

            fcout.write(bufferArray); //将两个缓冲区写入到同一个Channel中

            bufferArray[0].clear();
            bufferArray[1].clear();
        }
        fis.close();
        fos.close();
    }
}
