package code_02_character;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by 18351 on 2019/1/5.
 */
public class WriterDemo2 {
    public static void main(String[] args) throws IOException {
        OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream("demo1.txt"),"UTF-8");

        // 写数据
        // public void write(int c):写一个字符
        osw.write('a');
        osw.write(97);
        // 为什么数据没有进去呢?
        // 原因是：字符 = 2字节
        // 文件中数据存储的基本单位是字节。
        // void flush()

        // public void write(char[] cbuf):写一个字符数组
        // char[] chs = {'a','b','c','d','e'};
        // osw.write(chs);

        // public void write(char[] cbuf,int off,int len):写一个字符数组的一部分
        // osw.write(chs,1,3);

        // public void write(String str):写一个字符串
        // osw.write("我爱林青霞");

        // public void write(String str,int off,int len):写一个字符串的一部分
        osw.write("我爱林青霞", 2, 3);

        // 刷新缓冲区
        osw.flush();
        // osw.write("我爱林青霞", 2, 3);

        // 释放资源
        osw.close();
        // java.io.IOException: Stream closed
        // osw.write("我爱林青霞", 2, 3);
    }
}