package com.southeast.sequenceStream;

import java.io.*;

/**
 * 将两个文件中的内容写入同一个文件中
 *  a.txt    +   b.txt --> c.txt
 */
public class SequenceInputStreamDemo {
    public static void main(String[] args) throws IOException {
        FileInputStream fis=new FileInputStream("sequenceStream\\a.txt");
        FileInputStream fis2=new FileInputStream("sequenceStream\\b.txt");

        SequenceInputStream sis=new SequenceInputStream(fis,fis2);

        FileOutputStream fos=new FileOutputStream("sequenceStream\\d.txt");

        //一次读取一个字节数组
        byte[] bys=new byte[1024];
        int len=0;
        while((len=sis.read(bys))!=-1){
            fos.write(bys,0,len);
        }

        sis.close();
        fos.close();
    }
}
