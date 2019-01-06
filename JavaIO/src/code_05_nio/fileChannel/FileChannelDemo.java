package code_05_nio.fileChannel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 1. 开启FileChannel
 *
 */
public class FileChannelDemo {
    public static void main(String[] args) throws IOException {
        //1.创建一个RandomAccessFile（随机访问文件）对象通过RandomAccessFile对象的getChannel()方法。
        RandomAccessFile raf=new RandomAccessFile("demo6.txt","rw");
        FileChannel fc=raf.getChannel();

        //使用FileChannel的read()方法读取数据：
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        int bys=fc.read(byteBuffer);

        //使用FileChannel的write()方法写入数据：
        ByteBuffer byteBuffer2=ByteBuffer.allocate(1024);
        byteBuffer2.put("hello".getBytes());
        fc.write(byteBuffer2);

        //3.关闭FileChannel
        fc.close();
    }
}
