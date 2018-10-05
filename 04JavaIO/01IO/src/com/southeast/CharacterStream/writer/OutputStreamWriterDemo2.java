package com.southeast.CharacterStream.writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 字符输出流
 * OutputStreamWriter(OutputStream out):根据默认编码把字节流的数据转换为字符流
 * OutputStreamWriter(OutputStream out,String charsetName):根据指定编码把字节流数据转换为字符流
 *
 * 把字节流转换为字符流。
 * 字符流 = 字节流 +编码表。
 *
 * OutputStreamWriter的方法：
 *  public void write(int c):写一个字符
 *  public void write(char[] cbuf):写一个字符数组
 *  public void write(char[] cbuf,int off,int len):写一个字符数组的一部分
 *  public void write(String str):写一个字符串
 *  public void write(String str,int off,int len):写一个字符串的一部分
 *
 * 面试题：close()和flush()的区别?
 * A:close()关闭流对象，但是先刷新一次缓冲区。关闭之后，流对象不可以继续再使用了。
 * B:flush()仅仅刷新缓冲区,刷新之后，流对象还可以继续使用。
 */
public class OutputStreamWriterDemo2 {
    public static void main(String[] args) {
        OutputStreamWriter osw=null;
        try {
            //OutputStreamWriter(OutputStream out,String charsetName):根据指定编码把字节流数据转换为字符流
            osw=new OutputStreamWriter(new FileOutputStream("osw.txt",true),"UTF-8");

            // 写数据
            // public void write(int c):写一个字符
            osw.write('a');
            osw.write(97);
            // 为什么数据没有进去呢? （当没有关闭资源时）
            // 原因是：字符 = 2字节 文件中数据存储的基本单位是字节。
            // public void write(String str):写一个字符串
            osw.write("我爱林青霞");

            // public void write(String str,int off,int len):写一个字符串的一部分
            osw.write("我爱林青霞", 2, 3);
            osw.flush();//flush()刷新缓冲区
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
