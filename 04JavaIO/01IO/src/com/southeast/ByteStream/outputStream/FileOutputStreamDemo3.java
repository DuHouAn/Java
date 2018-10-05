package com.southeast.ByteStream.outputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 如何实现数据的换行?
 * 		为什么现在没有换行呢?因为你只写了字节数据，并没有写入换行符号。
 * 		如何实现呢?写入换行符号即可呗。
 * 		刚才我们看到了有写文本文件打开是可以的，通过windows自带的那个不行，为什么呢?
 * 		因为不同的系统针对不同的换行符号识别是不一样的?
 * 		    windows:\r\n
 * 		    linux:\n
 * 		    Mac:\r
 * 		而一些常见的个高级记事本，是可以识别任意换行符号的。
 *
 * 如何实现数据的追加写入?
 * 		用构造方法带第二个参数是true的情况即可
 */
public class FileOutputStreamDemo3 {
    public static void main(String[] args) {
        FileOutputStream outputStream=null;
        try {
            //outputStream=new FileOutputStream("fos3.txt");
            outputStream=new FileOutputStream("fos3.txt",true);//实现数据的追加写入
            for(int i=0;i<10000;i++){
                outputStream.write("hello".getBytes());
                outputStream.write("\r\n".getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
