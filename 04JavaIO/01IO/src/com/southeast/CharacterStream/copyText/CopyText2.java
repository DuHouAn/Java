package com.southeast.CharacterStream.copyText;

import java.io.*;

/**
 * 需求：把当前项目目录下的a.txt内容复制到当前项目目录下的b.txt中
 * 这里使用字符串缓冲流
 */
public class CopyText2 {
    public static void main(String[] args) throws IOException {
        String srcStr="a.txt";
        String destStr="b.txt";
        //copyText(srcStr,destStr);
        copyText2(srcStr,destStr);
    }

    public static void copyText(String srcStr,String destStr) throws IOException {
        BufferedReader bufferedReader=new BufferedReader(new FileReader(srcStr));
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(destStr));

        //读取一个字符数组
        char[] chs=new char[1024];
        int len=0;
        while((len=bufferedReader.read(chs))!=-1){
            bufferedWriter.write(chs,0,len);
            bufferedWriter.flush();
        }
        bufferedReader.close();
        bufferedWriter.close();
    }

    public static void copyText2(String srcStr,String destStr) throws IOException {
        BufferedReader bufferedReader=new BufferedReader(new FileReader(srcStr));
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(destStr));

        String line=null;
        while((line=bufferedReader.readLine())!=null){
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
