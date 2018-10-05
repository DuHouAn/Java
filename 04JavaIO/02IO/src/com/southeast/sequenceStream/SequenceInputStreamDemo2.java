package com.southeast.sequenceStream;

import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 将两个文件中的内容写入同一个文件中
 *  a.txt +  b.txt + c.txt  --> d.txt
 *
 * SequenceInputStream(Enumeration e)
 // 通过简单的回顾我们知道了Enumeration是Vector中的一个方法的返回值类型。
 // Enumeration<E> elements()
 */
public class SequenceInputStreamDemo2 {
    public static void main(String[] args) throws IOException {
        Vector<InputStream> vector=new Vector<InputStream>();

        InputStream is=new BufferedInputStream(new FileInputStream("sequenceStream\\a.txt"));
        InputStream is2=new BufferedInputStream(new FileInputStream("sequenceStream\\b.txt"));
        InputStream is3=new BufferedInputStream(new FileInputStream("sequenceStream\\c.txt"));
        vector.add(is);
        vector.add(is2);
        vector.add(is3);

        Enumeration<InputStream> en=vector.elements();

        SequenceInputStream sis=new SequenceInputStream(en);
        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream("sequenceStream\\d.txt"));

        byte[] bys=new byte[1024];
        int len=0;
        while((len=sis.read(bys))!=-1){
            bos.write(bys,0,len);
        }

        sis.close();
        bos.close();
    }
}
