package com.southeast.test8;

import java.io.File;
import java.io.FileFilter;

/**
 * * 判断liuyi目录下是否有后缀名为.txt的文件，如果有，就输出此文件名称
 * A:先获取所有的，然后遍历的时候，依次判断，如果满足条件就输出。
 * B:获取的时候就已经是满足条件的了，然后输出即可。
 *
 * 要想实现这个效果，就必须学习一个接口：文件名称过滤器
 * public String[] list(FilenameFilter filter)
 * public File[] listFiles(FilenameFilter filter)
 */
public class FileTest2 {
    public static void main(String[] args) {
        File file=new File("liuyi");

        File[] fileList=file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
              /*  if(!file.isDirectory()){
                    String fileName=file.getName();
                    if(fileName.endsWith(".txt")){
                        return true;
                    }
                }
                return false;*/
              return file.isFile() && file.getName().endsWith(".txt");
            }
        });

        if(fileList!=null){
            for(File f:fileList){
                System.out.println(f.getName());
            }
        }
    }
}
