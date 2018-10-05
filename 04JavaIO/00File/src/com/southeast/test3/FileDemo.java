package com.southeast.test3;

import java.io.File;

/**
 * 删除功能:public boolean delete()
 *
 * 注意：
 * 		A:如果你创建文件或者文件夹忘了写盘符路径，那么，默认在项目路径下。
 * 		B:Java中的删除不走回收站。
 * 		C:要删除一个文件夹，请注意该文件夹内不能包含文件或者文件夹
 */
public class FileDemo {
    public static void main(String[] args) {
        // 删除功能：我要删除demo\\a.txt这个文件
        File file = new File("demo\\a.txt");
        System.out.println("delete:" + file.delete());

        // 删除功能：我要删除liuyi\\a.txt这个文件夹
        File file2= new File("liuyi\\a.txt");
        System.out.println("delete:" + file2.delete());

        // 删除功能：我要删除aaa文件夹
        // File file5 = new File("aaa");
        // System.out.println("delete:" + file5.delete());

        //先删除 bbb文件夹，再删除aaa文件夹
        File file6 = new File("aaa\\bbb");
        File file7 = new File("aaa");
        System.out.println("delete:" + file6.delete());
        System.out.println("delete:" + file7.delete());
    }
}
