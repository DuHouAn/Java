package com.southeast.ByteStream.copyFile;

import java.io.*;

/**
 * 字节流四种方式复制文件：
 * 基本字节流一次读写一个字节：	共耗时：40411毫秒
 * 基本字节流一次读写一个字节数组： 共耗时：74毫秒
 * 高效字节流一次读写一个字节： 共耗时：484毫秒
 * 高效字节流一次读写一个字节数组： 共耗时：22毫秒
 */
public class CopyMP4 {
    public static void main(String[] args) throws IOException {
        long start=System.currentTimeMillis();

        String srcStr="歌唱祖国.mp4";
        //String destStr="copy1.mp4";
        //String destStr="copy2.mp4";
        //String destStr="copy3.mp4";
        String destStr="copy4.mp4";
        //method(srcStr,destStr);
        //method2(srcStr,destStr);
        //method3(srcStr,destStr);
        method4(srcStr,destStr);
        long end=System.currentTimeMillis();
        System.out.println("复制用时："+(end-start)+"毫秒");
    }

    //基本字节流一次读写一个字节
    public static void method(String srcStr,String destStr) throws IOException {
        InputStream inputStream=new FileInputStream(srcStr);
        OutputStream outputStream=new FileOutputStream(destStr);

        //一次读写一个字节
        int bys=0;
        while((bys=inputStream.read())!=-1){
            outputStream.write(bys);
        }

        inputStream.close();
        outputStream.close();
    }

    //基本字节流一次读写一个字节数组
    public static void method2(String srcStr,String destStr) throws IOException {
        InputStream inputStream=new FileInputStream(srcStr);
        OutputStream outputStream=new FileOutputStream(destStr);

        //一次读写一个字节数组
        byte[] bys=new byte[1024];
        int len=0;
        while((len=inputStream.read(bys))!=-1){
            outputStream.write(bys,0,len);
        }

        inputStream.close();
        outputStream.close();
    }

    //高效字节流一次读写一个字节
    public static void method3(String srcStr,String destStr) throws IOException {
        BufferedInputStream inputStream=new BufferedInputStream(new FileInputStream(srcStr));
        BufferedOutputStream outputStream=new BufferedOutputStream(new FileOutputStream(destStr));

        //一次读写一个字节
        int bys=0;
        while((bys=inputStream.read())!=-1){
            outputStream.write(bys);
        }

        inputStream.close();
        outputStream.close();
    }

    //高效字节流一次读写一个字节数组
    public static void method4(String srcStr,String destStr) throws IOException {
        BufferedInputStream inputStream=new BufferedInputStream(new FileInputStream(srcStr));
        BufferedOutputStream outputStream=new BufferedOutputStream(new FileOutputStream(destStr));

        //一次读写一个字节数组
        byte[] bys=new byte[1024];
        int len=0;
        while((len=inputStream.read(bys))!=-1){
            outputStream.write(bys,0,len);
        }

        inputStream.close();
        outputStream.close();
    }
}
