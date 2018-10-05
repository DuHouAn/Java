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
public class FileInputStreamDemo2 {
    public static void main(String[] args) {
        FileInputStream inputStream=null;
        try {
            inputStream=new FileInputStream("fos3.txt");

            //读取整个文件
            //数组的长度一般是1024或者1024的整数倍
            byte[] bys=new byte[1024];
            int len=0;
            while((len=inputStream.read(bys))!=-1){//inputStream.read(bys) 一次读取一个字节数组
                String str=new String(bys,0,len);
                System.out.print(str);
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
