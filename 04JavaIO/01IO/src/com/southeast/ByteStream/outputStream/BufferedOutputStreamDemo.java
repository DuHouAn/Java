package com.southeast.ByteStream.outputStream;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 使用
 * BufferedOutputStream 读物字节数据
 */
public class BufferedOutputStreamDemo {
    public static void main(String[] args) {
        BufferedOutputStream bos=null;
        try {
            bos=new BufferedOutputStream(new FileOutputStream("bos.txt"));
            bos.write("hello".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bos!=null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
