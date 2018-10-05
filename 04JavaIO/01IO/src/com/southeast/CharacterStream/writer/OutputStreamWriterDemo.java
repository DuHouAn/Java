package com.southeast.CharacterStream.writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 字符输出流
 * OutputStreamWriter(OutputStream out):根据默认编码把字节流的数据转换为字符流
 * OutputStreamWriter(OutputStream out,String charsetName):根据指定编码把字节流数据转换为字符流
 *
 * 把字节流转换为字符流。
 * 字符流 = 字节流 +编码表。
 */
public class OutputStreamWriterDemo {
    public static void main(String[] args) {
        OutputStreamWriter osw=null;
        try {
            //OutputStreamWriter(OutputStream out):根据默认编码把字节流的数据转换为字符流
            //osw=new OutputStreamWriter(new FileOutputStream("osw.txt"));

            //OutputStreamWriter(OutputStream out,String charsetName):根据指定编码把字节流数据转换为字符流
            osw=new OutputStreamWriter(new FileOutputStream("osw.txt"),"UTF-8");
            osw.write("中国");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(osw!=null){
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
