package code_02_character;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * InputStreamReader(InputStream is) //用默认的编码读取数据
 * InputStreamReader(InputStream is,String charsetName) //用指定的编码读取数据
 */
public class ReaderDemo2 {
    public static void main(String[] args) throws IOException {
        InputStreamReader isr=new InputStreamReader(
                new FileInputStream("demo1.txt"),"UTF-8");

        // 一次读取一个字符
        // int ch = 0;
        // while ((ch = isr.read()) != -1) {
        // System.out.print((char) ch);
        // }

        // 一次读取一个字符数组
        char[] chs = new char[1024];
        int len = 0;
        while ((len = isr.read(chs)) != -1) {
            System.out.print(new String(chs, 0, len));
        }

        isr.close();
    }
}
