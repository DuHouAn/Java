package com.southeast.test;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by 18351 on 2018/9/2.
 */
public class IOTest3 {
    public static void main(String[] args) throws IOException {
       /* ArrayList<String> list=new ArrayList<String>();

        for(int i=0;i<10000;i++){
            list.add("hello"+i);
        }
        arrayListToFile(list);*/

       ArrayList<String> list=fileToArrayList("list.txt");
        System.out.println(list.size());
    }

    /**
     * 需求：把ArrayList集合中的字符串数据存储到文本文件
     *
     * 分析：
     * 		通过题目的意思我们可以知道如下的一些内容，
     * 			ArrayList集合里存储的是字符串。
     * 			遍历ArrayList集合，把数据获取到。
     * 			然后存储到文本文件中。
     * 			文本文件说明使用字符流。
     *
     * 数据源：
     * 		ArrayList<String> -- 遍历得到每一个字符串数据
     * 目的地：
     * 		a.txt -- FileWriter -- BufferedWriter
     */
    public static void arrayListToFile(ArrayList<String> list) throws IOException {
        BufferedWriter bw=new BufferedWriter(new FileWriter("list.txt"));

        for(String str:list){
            bw.write(str);
            bw.newLine();
            bw.flush();
        }
        bw.close();
    }

    /**
     * 需求：从文本文件中读取数据(每一行为一个字符串数据)到集合中，并遍历集合
     * 分析：
     * 		通过题目的意思我们可以知道如下的一些内容，
     * 			数据源是一个文本文件。
     * 			目的地是一个集合。
     * 			而且元素是字符串。
     *
     * 数据源：
     * 		b.txt -- FileReader -- BufferedReader
     * 目的地：
     * 		ArrayList<String>
     */
    public static ArrayList<String> fileToArrayList(String src) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(src));

        ArrayList<String> list=new ArrayList<String>();

        String line=null;
        while((line=br.readLine())!=null){
            list.add(line);
        }
        return list;
    }
}
