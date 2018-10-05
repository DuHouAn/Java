package com.southeast.test4;

import java.io.*;
import java.util.Arrays;

/**
 * 已知test4\\a.txt文件中有这样的一个字符串：“hcexfgijkamdnoqrzstuvwybpl”
 * 请编写程序读取数据内容，把数据排序后写入b.txt中。
 */
public class SortString {
    public static void main(String[] args) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader("test4\\a.txt"));
        BufferedWriter bw=new BufferedWriter(new FileWriter("test4\\b.txt"));

        //读取文件中数据(因为只有一行，就使用readLine)
        String line=br.readLine();

        //排序
        char[] chs=line.toCharArray();
        Arrays.sort(chs);
        String newLine=new String(chs);

        //将数据写入文件
        bw.write(newLine);
        bw.flush();

        br.close();
    }
}
