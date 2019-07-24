package code_00_disk;

import java.io.File;

/**
 * Created by 18351 on 2019/1/4.
 */
public class FileDemo5 {
    public static void main(String[] args) {
        // 创建文件对象
        File file = new File("demo2\\a.txt");

        System.out.println("isDirectory:" + file.isDirectory());// false
        System.out.println("isFile:" + file.isFile());// true
        System.out.println("exists:" + file.exists());// true
        System.out.println("canRead:" + file.canRead());// true
        System.out.println("canWrite:" + file.canWrite());// true
        System.out.println("isHidden:" + file.isHidden());// false
    }
}
