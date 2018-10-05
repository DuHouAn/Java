package com.southeast.test;

import java.io.*;

/**
 * * 复制图片
 *
 * 分析：
 * 		复制数据，如果我们知道用记事本打开并能够读懂，就用字符流，否则用字节流。
 * 		通过该原理，我们知道我们应该采用字节流。
 * 		而字节流有4种方式，所以做这个题目我们有4种方式。推荐掌握第4种。
 *
 * 数据源：
 * 		海边.jpg -- FileInputStream -- BufferedInputStream
 * 目的地：
 * 		haibian.jpg -- FileOutputStream -- BufferedOutputStream
 */
public class IOTest2 {
    public static void main(String[] args) throws IOException {
        long start=System.currentTimeMillis();
        String srcStr="海边.jpg";
        String destStr="haibian.jpg";
        //method(srcStr,destStr);
        //method2(srcStr,destStr);
        //method3(srcStr,destStr);
        method4(srcStr,destStr);
        long end=System.currentTimeMillis();
        System.out.println("程序执行时间"+(end-start)+"毫秒");
    }

    //字节缓冲流一次读写一个字节数组
    public static void method4(String srcStr,String destStr) throws IOException { //3 ms
        BufferedInputStream bis=new BufferedInputStream(new FileInputStream(srcStr));
        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(destStr));

        byte[] bys=new byte[1024];
        int len=0;
        while((len=bis.read(bys))!=-1){
            bos.write(bys,0,len);
        }

        bis.close();
        bos.close();
    }

    //字节缓冲流一次读写一个字节
    public static void method3(String srcStr,String destStr) throws IOException { //24 ms
       BufferedInputStream bis=new BufferedInputStream(new FileInputStream(srcStr));
       BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(destStr));

       int chs=0;
       while((chs=bis.read())!=-1){
           bos.write(chs);
       }

       bis.close();
       bos.close();
    }

    //基本字节流一次读写一个字节数组
    public static void method2(String srcStr,String destStr) throws IOException { //5 ms
        FileInputStream fis=new FileInputStream(srcStr);
        FileOutputStream fos=new FileOutputStream(destStr);

        //一次读写一个字节数组
        byte[] bys=new byte[1024];
        int len=0;
        while((len=fis.read(bys))!=-1){
            fos.write(bys,0,len);
        }

        fis.close();
        fos.close();
    }

    //基本字节流一次读写一个字节
    public static void method(String srcStr,String destStr) throws IOException { //1685 ms
        FileInputStream fis=new FileInputStream(srcStr);
        FileOutputStream fos=new FileOutputStream(destStr);

        int bys=0;
        while((bys=fis.read())!=-1){
            fos.write(bys);
        }

        fis.close();
        fos.close();
    }
}
