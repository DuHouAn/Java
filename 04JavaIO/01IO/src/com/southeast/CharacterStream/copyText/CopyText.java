package com.southeast.CharacterStream.copyText;

import java.io.*;

/**
 * 需求：把当前项目目录下的a.txt内容复制到当前项目目录下的b.txt中
 */
public class CopyText {
    public static void main(String[] args) throws IOException {
        String srcStr="a.txt";
        String destStr="b.txt";
        //copyText(srcStr,destStr);
        //copyText2(srcStr,destStr);
        copyText3(srcStr,destStr);
    }

    /**
     * 数据源：
     * 		a.txt -- 读取数据 -- 字符转换流 -- InputStreamReader
     * 目的地：
     * 		b.txt -- 写出数据 -- 字符转换流 -- OutputStreamWriter
     */
    public static void copyText(String srcStr,String destStr) throws IOException {
        InputStreamReader isr=new InputStreamReader(new FileInputStream(srcStr));
        OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(destStr));

        //一次读取一个字符
       /* int chs;
        while((chs=isr.read())!=-1){
            osw.write(chs);
        }*/

        //一次读取一个字符数组
        char[] chs=new char[1024];
        int len=0;
        while((len=isr.read(chs))!=-1){
            osw.write(chs,0,len);//注意字符串流写入，一定要记得flush
            osw.flush();
        }
        isr.close();
        osw.close();
    }

    /**
     * 由于我们常见的操作都是使用本地默认编码，所以，不用指定编码。
     * 而转换流的名称有点长，所以，Java就提供了其子类供我们使用。
     *  OutputStreamWriter = FileOutputStream + 编码表(GBK)
     *      FileWriter = FileOutputStream + 编码表(GBK)
     *
     *  InputStreamReader = FileInputStream + 编码表(GBK)
     *      FileReader = FileInputStream + 编码表(GBK)
     */
    public static void copyText2(String srcStr,String destStr) throws IOException {
        FileReader fr=new FileReader(srcStr);
        FileWriter fw=new FileWriter(destStr);

        //一次读取一个字符
        int chs;
        while((chs=fr.read())!=-1){
            fw.write(chs);
        }

        fr.close();
        fw.close();
    }

    public static void copyText3(String srcStr,String destStr) throws IOException {
        FileReader fr=new FileReader(srcStr);
        FileWriter fw=new FileWriter(destStr);

        //一次读取一个字符数组
        char[] chs=new char[1024];
        int len=0;
        while((len=fr.read(chs))!=-1){
            fw.write(chs,0,len);
            fw.flush();//flush()刷新缓冲区,刷新之后，流对象还可以继续使用。
        }

        fr.close();
        fw.close();
    }
}
