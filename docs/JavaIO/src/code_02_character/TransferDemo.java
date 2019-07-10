package code_02_character;

import java.io.*;

/**
 * Created by 18351 on 2019/1/5.
 */
public class TransferDemo {
    public static void main(String[] args) throws IOException {
        // 封装数据源
        BufferedReader br = new BufferedReader(new FileReader("demo1.txt"));
        // 封装目的地
        BufferedWriter bw = new BufferedWriter(new FileWriter("demo4.txt"));

        // 两种方式其中的一种一次读写一个字符数组
        char[] chs = new char[1024];
        int len = 0;
        while ((len = br.read(chs)) != -1) {
            bw.write(chs, 0, len);
            bw.flush();
        }

        // 释放资源
        bw.close();
        br.close();
    }
}
