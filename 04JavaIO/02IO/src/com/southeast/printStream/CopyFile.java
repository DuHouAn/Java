package com.southeast.printStream;

import java.io.*;

/**
 *需求：DataStreamDemo.java复制到Copy.java中
 * 数据源：
 * 		DataStreamDemo.java -- 读取数据 -- FileReader -- BufferedReader
 * 目的地：
 * 		Copy.java -- 写出数据 -- FileWriter -- BufferedWriter -- PrintWriter
 */
public class CopyFile {
    public static void main(String[] args) throws IOException {
        String srcStr="br.txt";
        String destStr="bw.txt";
        //copyFile(srcStr,destStr);
        copyFile2(srcStr,destStr);
    }

    //老版本
    public static void copyFile(String srcStr,String destStr) throws IOException {
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

    //打印流的改进版
    public static void copyFile2(String srcStr,String destStr) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(srcStr));
        PrintWriter pw=new PrintWriter(new FileWriter(destStr));//字符打印流

        String line=null;
        while((line=br.readLine())!=null){
           pw.println(line);
        }

        br.close();
        pw.close();
    }
}
