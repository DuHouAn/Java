package code_00_disk;

import java.io.File;

/**
 * Created by 18351 on 2019/1/4.
 */
public class FileDemo4 {
    public static void main(String[] args) {
        File file=new File("demo1\\a.txt");
        File file2=new File("demo2\\a.txt");
        System.out.println("rename:"+ file.renameTo(file2));
    }
}
