package com.southeast.test4;

import java.io.File;

/**
 ** 重命名功能:public boolean renameTo(File dest)
 * 		如果路径名相同，就是改名。
 * 		如果路径名不同，就是改名并剪切。
 */
public class FileDemo {
    public static void main(String[] args) {
        //将 demo\\a.txt 重命名为deme\\b.txt
        File file=new File("demo\\a.txt");
        File destFile=new File("demo\\b.txt");
        System.out.println("rename:"+file.renameTo(destFile));

        //将 demo\\b.txt 剪切到 liuyi\\
        File destFile2=new File("liuyi\\a.txt");
        System.out.println("cut:"+destFile.renameTo(destFile2));
    }
}
