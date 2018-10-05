package com.southeast.CharacterStream.reader;

import java.io.*;

/**
 * 字符输入流1
 * InputStreamReader(InputStream is):用默认的编码读取数据
 * InputStreamReader(InputStream is,String charsetName):用指定的编码读取数据
 */
public class InputStreamReaderDemo {
    public static void main(String[] args) {
        InputStreamReader isr=null;
        try {
            isr=new InputStreamReader(new FileInputStream("osw.txt"),"UTF-8");

            int ch=0;
            while((ch=isr.read())!=-1){
                System.out.print((char)ch);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
