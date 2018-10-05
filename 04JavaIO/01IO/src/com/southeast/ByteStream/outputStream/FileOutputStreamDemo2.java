package com.southeast.ByteStream.outputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 字节输出流操作步骤：
 * A:创建字节输出流对象
 * B:调用write()方法
 * C:释放资源
 *
 * public void write(int b):写一个字节
 * public void write(byte[] b):写一个字节数组
 * public void write(byte[] b,int off,int len):写一个字节数组的一部分
 */
public class FileOutputStreamDemo2 {
    public static void main(String[] args) {
        FileOutputStream outputStream=null;
        try {
            outputStream=new FileOutputStream("fos2.txt");
            outputStream.write(97);
            outputStream.write("b".getBytes());
            outputStream.write("cdef".getBytes(),2,2);
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
