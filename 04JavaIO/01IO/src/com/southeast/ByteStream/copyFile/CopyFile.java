package com.southeast.ByteStream.copyFile;

import java.io.*;

/**
 *
 * 复制文本文件。
 * 数据源：从哪里来
 * a.txt	--	读取数据	--	FileInputStream
 *
 * 目的地：到哪里去
 * b.txt	--	写数据		--	FileOutputStream
 *
 * java.io.FileNotFoundException: a.txt (系统找不到指定的文件。)
 *
 * 这一次复制中文没有出现任何问题，为什么呢?
 * 上一次我们出现问题的原因在于我们每次获取到一个字节数据，就把该字节数据转换为了字符数据，然后输出到控制台。
 * 而这一次呢?确实通过IO流读取数据，写到文本文件，你读取一个字节，我就写入一个字节，你没有做任何的转换。
 * 它会自己做转换。
 */
public class CopyFile {
    public static void main(String[] args) throws IOException {
       /* InputStream srcStream=new FileInputStream("a.txt");
        OutputStream destStream=new FileOutputStream("b.txt");
        copyFile(srcStream,destStream);*/

       /*InputStream srcStream=new FileInputStream("歌唱祖国.mp4");
       OutputStream destStream=new FileOutputStream("test.mp4");
       copyMP4(srcStream,destStream);*/

       InputStream srcStream=new FileInputStream("海边.jpg");
       OutputStream destStream=new FileOutputStream("sea.jpg");
       copyImage(srcStream,destStream);

        srcStream.close();
        destStream.close();
    }

    public static void copyFile(InputStream srcStream, OutputStream destStream) throws IOException {
        //读取单个字节，然后再复制到文件中
       /*
       int by=0;
        while((by=srcStream.read())!=-1){
            destStream.write(by);
        }*/

       //读取字节数组，然后再复制字节数组到文件中
        byte[] bys=new byte[1024];
        int len=0;
        while((len= srcStream.read(bys))!=-1){
            destStream.write(bys,0,len);
        }
    }

    public static void copyImage(InputStream srcStream, OutputStream destStream) throws IOException {
        int by=0;
        while((by=srcStream.read())!=-1){
            destStream.write(by);
        }
    }

    public static void copyMP4(InputStream srcStream, OutputStream destStream) throws IOException {
        byte[] bys=new byte[1024];
        int len=0;
        while((len=srcStream.read(bys))!=-1){
            destStream.write(bys,0,len);
        }
    }
}
