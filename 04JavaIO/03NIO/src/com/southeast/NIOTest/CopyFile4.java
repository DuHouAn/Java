package com.southeast.NIOTest;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用通道通信复制文件
 */
public class CopyFile4 {
    public static void main(String[] args) throws IOException {
        long start=System.currentTimeMillis();

        File srcFile=new File("歌唱祖国.mp4");
        File destFile=new File("copy3.mp4");
        copyFile(srcFile,destFile); //使用通道通信复制文件
        copyFile2(srcFile,destFile);

        long end=System.currentTimeMillis();
        System.out.println("运行程序用时："+(end-start)+"毫秒");
    }

    public static void copyFile(File srcFile,File destFile) throws IOException { //48
        FileInputStream fis=new FileInputStream(srcFile);
        FileOutputStream fos=new FileOutputStream(destFile);

        //获取输入字节流的文件通道
        FileChannel fcin = fis.getChannel();
        //获取输出字节流的文件通道
        FileChannel fcout=fos.getChannel();

        long position=0;
        long count=fcin.size();
        fcout.transferFrom(fcin,position,count);

        fis.close();
        fos.close();
    }

    public static void copyFile2(File srcFile,File destFile) throws IOException { //164
        FileInputStream fis=new FileInputStream(srcFile);
        FileOutputStream fos=new FileOutputStream(destFile);

        byte[] bys=new byte[1024];
        int len=0;
        while((len=fis.read(bys))!=-1){
            fos.write(bys,0,len);
        }

        fis.close();
        fos.close();
    }
}
