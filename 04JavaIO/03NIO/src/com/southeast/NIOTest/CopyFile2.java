package com.southeast.NIOTest;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  复制文件
 */
public class CopyFile2 {
    public static void main(String[] args) throws IOException {
        long start=System.currentTimeMillis();

        File srcFile=new File("歌唱祖国.mp4");
        File destFile=new File("copy2.mp4");
        copyFile2(srcFile,destFile);

        long end=System.currentTimeMillis();
        System.out.println("运行程序用时："+(end-start)+"毫秒");
    }

    public static void copyFile(File srcFile,File destFile) throws IOException { // 265 ms
        FileInputStream fis=new FileInputStream(srcFile);
        FileOutputStream fos=new FileOutputStream(destFile);

        FileChannel fcin=fis.getChannel();
        FileChannel fcout=fos.getChannel();

        ByteBuffer byteBuffer=ByteBuffer.allocateDirect(1024);
        int by=0;
        while((by=fcin.read(byteBuffer))!=-1){
            //TODO:切换读写
            byteBuffer.flip();//缓存区由写切换到读
            fcout.write(byteBuffer);
            byteBuffer.clear();//清空缓存区
        }

        fis.close();
        fos.close();
    }

    public static void copyFile2(File srcFile,File destFile) throws IOException { // 169 ms
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
