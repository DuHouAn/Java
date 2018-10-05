package com.southeast.randomAccessFile;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * * 随机访问流：
 * 		TODO:RandomAccessFile类不属于流，是Object类的子类。
 * 		但它融合了InputStream和OutputStream的功能。
 * 		支持对文件的随机访问读取和写入。
 *
 * public RandomAccessFile(String name,String mode)：
 *      第一个参数是文件路径，第二个参数是操作文件的模式。
 * 		    模式有四种，我们最常用的一种叫"rw",这种方式表示我既可以写数据，也可以读取数据
 * getFilePointer() 获取当前指针位置
 */
public class RandomAccessFileDemo {
    public static void main(String[] args) throws IOException {
        //write();
        read();
    }

    public static void read() throws IOException {
        RandomAccessFile raf=new RandomAccessFile("raf.txt","rw");

        int i = raf.readInt();
        System.out.println(i);
        // 该文件指针可以通过 getFilePointer方法读取，并通过 seek 方法设置。
        System.out.println("当前文件的指针位置是：" + raf.getFilePointer());

        char ch = raf.readChar();
        System.out.println(ch);
        System.out.println("当前文件的指针位置是：" + raf.getFilePointer());

        String s = raf.readUTF();
        System.out.println(s);
        System.out.println("当前文件的指针位置是：" + raf.getFilePointer());

        // 该文件指针可以通过 getFilePointer方法读取，并通过 seek 方法设置。 设置 seek()中值为4，则从4位置开始读取
        raf.seek(4);//若没有此设置，则java.io.EOFException
        ch = raf.readChar();
        System.out.println(ch);

        raf.close();
    }

    public static void write() throws IOException {
        RandomAccessFile raf=new RandomAccessFile("raf.txt","rw");

        raf.writeInt(100);
        raf.writeChar('a');
        raf.writeUTF("中国");

        raf.close();
    }
}
