package com.southeast.test2;

import java.io.File;
import java.io.IOException;

/**
 * *创建功能：
 *public boolean createNewFile():创建文件 如果存在这样的文件，就不创建了
 *public boolean mkdir():创建文件夹 如果存在这样的文件夹，就不创建了
 *public boolean mkdirs():创建文件夹,如果父文件夹不存在，会帮你创建出来
 *
 *骑白马的不一定是王子，可能是班长。
 */
public class FileDemo {
    public static void main(String[] args) throws IOException {
        makeDirDemo("demo");
        createAtxtFile("demo\\a.txt");
        //createBtxtFile("test\\b.txt");
        // Exception in thread "main" java.io.IOException: 系统找不到指定的路径。
        // 注意：要想在某个目录下创建内容，该目录首先必须存在。

        createAAA("test\\aaa");

        //注意： 看下面的这个东西：
        File file8 = new File("liuyi\\a.txt");
        System.out.println("mkdirs:" + file8.mkdirs());//得到的是名为a.txt的文件夹 -->骑白马的不一定都是王子
    }

    //创建demo文件夹
    public static void makeDirDemo(String filePath) {
        File file=new File(filePath);
        System.out.println("mkdir:"+file.mkdir());
    }

    //在demo下创建一个文件a.txt
    public static void createAtxtFile(String filePath) throws IOException {
        File file=new File(filePath);
        System.out.println("createNewFile:"+file.createNewFile());
    }

    //目录test下创建一个文件b.txt
    public static void createBtxtFile(String filePath) throws IOException {
        File file=new File(filePath);
        System.out.println("createNewFile:"+file.createNewFile());
    }

    //目录test下创建aaa目录
    public static void createAAA(String filePath) throws IOException {
        File file=new File(filePath);
        System.out.println("mkdirs:"+file.mkdirs());
    }

}
