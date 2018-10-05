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
 *  聚集（gather）写入一个Channel，是指在写操作时将多个buffer的数据写入同一个Channel，
 *  因此，多个Buffer中的数据将“聚集（gather）”后写入到一个Channel。

 *  scatter/gather经常用于需要将传输的数据分开处理的场合，
 *  例如传输一个由消息头和消息体组成的消息，你可能会将消息体和消息头分散到不同的buffer中，
 *  这样你可以方便的处理消息头和消息体。
 *
 *  Gathering Writes
 *  是指数据从多个buffer写入到同一个channel
 */
public class GatherWritingDemo {
    public static void main(String[] args) throws IOException {
        FileInputStream fis=new FileInputStream("a.txt");
        FileOutputStream fos=new FileOutputStream("b.txt");

        FileChannel fcin = fis.getChannel();
        FileChannel fcout=fos.getChannel();

        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);

        ByteBuffer[] bufferArray = { header, body };

        long bys=0;
        while((bys= fcin.read(bufferArray))!=-1){
            for(ByteBuffer byteBuffer:bufferArray){
                byteBuffer.flip();
            }

            fcout.write(bufferArray);
            /**
             *  fcout.write(bufferArray);
             *  buffers数组是write()方法的输入参数，
             *  write()方法会按照buffer在数组中的顺序，将数据写入到channel，
             *  注意只有position和limit之间的数据才会被写入。
             *  因此，如果一个buffer的容量为128 byte，但是仅仅包含58byte的数据，那么这58byte的数据将被写入到channel中。
             *  因此与Scattering Reads相反，Gathering Writes能较好的处理动态消息。
             */

            for(ByteBuffer byteBuffer:bufferArray){
                byteBuffer.clear();
            }
        }
        fis.close();
        fos.close();
    }
}
