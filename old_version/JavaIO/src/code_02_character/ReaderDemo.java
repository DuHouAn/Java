package code_02_character;

import java.io.*;

/**
 * InputStreamReader(InputStream is) //用默认的编码读取数据
 * InputStreamReader(InputStream is,String charsetName) //用指定的编码读取数据
 */
public class ReaderDemo {
    public static void main(String[] args) throws IOException {
        InputStreamReader isr=new InputStreamReader(
                new FileInputStream("demo1.txt"),"UTF-8");
        int ch=0;
        while((ch=isr.read())!=-1){
            System.out.print((char)ch);
        }

        isr.close();
    }
}
