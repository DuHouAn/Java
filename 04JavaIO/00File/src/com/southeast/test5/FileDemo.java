package com.southeast.test5;

import java.io.File;

/**
 * * 判断功能:
 * public boolean isDirectory():判断是否是目录
 * public boolean isFile():判断是否是文件
 * public boolean exists():判断是否存在
 * public boolean canRead():判断是否可读
 * public boolean canWrite():判断是否可写
 * public boolean isHidden():判断是否隐藏
 */
public class FileDemo {
    public static void main(String[] args) {
        // 创建文件对象
        File file = new File("liuyi\\a.txt");

        System.out.println("isDirectory:" + file.isDirectory());// false
        System.out.println("isFile:" + file.isFile());// true
        System.out.println("exists:" + file.exists());// true
        System.out.println("canRead:" + file.canRead());// true
        System.out.println("canWrite:" + file.canWrite());// true
        System.out.println("isHidden:" + file.isHidden());// false
    }
}
