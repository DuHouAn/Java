package com.southeast.CharacterStream.reader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 字符输入流
 * InputStreamReader(InputStream is):用默认的编码读取数据
 * InputStreamReader(InputStream is,String charsetName):用指定的编码读取数据
 *
 *  InputStreamReader的方法：
 * int read():一次读取一个字符
 * int read(char[] chs):一次读取一个字符数组
 */
public class InputStreamReaderDemo2 {
    public static void main(String[] args) {
        InputStreamReader isr=null;
        try {
            isr=new InputStreamReader(new FileInputStream("osw.txt"),"UTF-8");

            //一次读取一个字符
           /* int ch=0;
            while((ch=isr.read())!=-1){
                System.out.print((char)ch);
            }*/

           //一次读取一个字符数组
            char[] chs=new char[1024];
            int len=0;
            while((len=isr.read(chs))!=-1){
                System.out.print(new String(chs,0,len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
