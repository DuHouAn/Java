package com.southeast.ByteStream.outputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 字节输出流FileOutputStream
 *
 * 查看FileOutputStream的构造方法：
 * 		FileOutputStream(File file)
 *		FileOutputStream(String name)
 *
 * 字节输出流操作步骤：
 * 		A:创建字节输出流对象
 * 		B:写数据
 * 		C:释放资源
 */
public class FileOutputStreamDemo {
    public static void main(String[] args){
        //向指定文件中写入数据 要使用输出流
        //A:创建字节输出流对象
        OutputStream outputStream=null;
        try {
            outputStream=new FileOutputStream("fos.txt"); //如果该文件不存在，则创建该文件
            //B:写数据
            outputStream.write("I am a Chinese.".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(outputStream!=null){
                try {
                    //C:释放资源
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
