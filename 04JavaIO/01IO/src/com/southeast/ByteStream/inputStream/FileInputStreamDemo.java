package com.southeast.ByteStream.inputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 字节输入流操作步骤：
 * A:创建字节输入流对象
 * B:调用read()方法读取数据，并把数据显示在控制台
 * C:释放资源
 *
 * 读取数据的方式：
 * A:int read():一次读取一个字节
 * B:int read(byte[] b):一次读取一个字节数组
 */
public class FileInputStreamDemo {
    public static void main(String[] args) {
        FileInputStream inputStream=null;
        try {
            inputStream=new FileInputStream("fos3.txt");

            //读取整个文件
            int bys=inputStream.read();
            while(bys!=-1){
                System.out.print((char)bys);
                bys=inputStream.read();//一次读取一个字节
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
