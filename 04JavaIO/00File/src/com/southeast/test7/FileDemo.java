package com.southeast.test7;

import java.io.File;

/**
 * 获取功能：
 * public String[] list():获取指定目录下的所有文件或者文件夹的名称数组
 * public File[] listFiles():获取指定目录下的所有文件或者文件夹的File数组
 */
public class FileDemo {
    public static void main(String[] args) {
        File file=new File("liuyi");

        //public String[] list():获取指定目录下的所有文件或者文件夹的名称数组
        String[] fileNames=file.list();
        if(fileNames!=null){
            for(String fileName:fileNames){
                System.out.println(fileName);
            }
        }
        System.out.println("==================");

        //public File[] listFiles():获取指定目录下的所有文件或者文件夹的File数组
        File[] fileList=file.listFiles();
        if(fileList!=null){
            for(File f:fileList){
                System.out.println(f.getName());
            }
        }
    }
}
