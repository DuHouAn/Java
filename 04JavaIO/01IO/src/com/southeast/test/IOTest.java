package com.southeast.test;

import java.io.*;

/**
 * 复制文本文件
 * 使用字符流
 * 分析：
 * 		复制数据，如果我们知道用记事本打开并能够读懂，就用字符流，否则用字节流。
 * 		通过该原理，我们知道我们应该采用字符流更方便一些。
 * 		而字符流有5种方式，所以做这个题目我们有5种方式。推荐掌握第5种。
 * 数据源：
 * 		a.txt -- FileReader -- BufferdReader
 * 目的地：
 * 		c.txt -- FileWriter -- BufferedWriter
 */
public class IOTest {
    public static void main(String[] args) throws IOException {
        long start=System.currentTimeMillis();
        String srcStr="a.txt";
        String destStr="b.txt";
        //method(srcStr,destStr);
        //method2(srcStr,destStr);
        //method3(srcStr,destStr);
        //method4(srcStr,destStr);
        method5(srcStr,destStr);
        long end=System.currentTimeMillis();
        System.out.println("程序执行时间"+(end-start)+"毫秒");
    }

    //字符缓冲流一次读写一个字符串
    public static void method5(String srcStr,String destStr) throws IOException { // 400 ms 因为刷新比较消耗时间
        BufferedReader br=new BufferedReader(new FileReader(srcStr));
        BufferedWriter bw=new BufferedWriter(new FileWriter(destStr));

        String line=null;
        while((line=br.readLine())!=null){
            bw.write(line);
            bw.newLine();
            bw.flush();
        }

        br.close();
        bw.close();
    }

    //字符缓冲流一次读写一个字符数组
    public static void method4(String srcStr,String destStr) throws IOException { //23 ms
        BufferedReader br=new BufferedReader(new FileReader(srcStr));
        BufferedWriter bw=new BufferedWriter(new FileWriter(destStr));

        char[] chs=new char[1024];
        int len=0;
        while((len=br.read(chs))!=-1){
            bw.write(chs,0,len);
        }

        br.close();
        bw.close();
    }

    //字符缓冲流一次读写一个字符
    public static void method3(String srcStr,String destStr) throws IOException { //75ms
        BufferedReader br=new BufferedReader(new FileReader(srcStr));
        BufferedWriter bw=new BufferedWriter(new FileWriter(destStr));

        int chs=0;
        while((chs=br.read())!=-1){
            bw.write(chs);
        }

        br.close();
        bw.close();
    }

    //基本字符流一次读写一个字符数组
    public static void method2(String srcStr,String destStr) throws IOException { //23 ms
        FileReader fr=new FileReader(srcStr);
        FileWriter fw=new FileWriter(destStr);

        //一次读写一个字符数组
        char[] chs=new char[1024];
        int len=0;
        while((len=fr.read(chs))!=-1){
            fw.write(chs,0,len);
        }

        fr.close();
        fw.close();
    }

    //基本字符流一次读写一个字符
    public static void method(String srcStr,String destStr) throws IOException { //265 ms
        FileReader fr=new FileReader(srcStr);
        FileWriter fw=new FileWriter(destStr);

        //一次读写一个字符
        int ch=0;
        while((ch=fr.read())!=-1){
            fw.write(ch);
        }

        fr.close();
        fw.close();
    }
}
