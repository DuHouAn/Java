package code_00_disk;

import java.io.File;
import java.io.IOException;

/**
 * 删除功能
 */
public class FileDemo3 {
    public static void main(String[] args) throws IOException {
        // 删除功能：我要删除demo\\a.txt这个文件
        File file = new File("demo\\a.txt");
        System.out.println("delete:" + file.delete());
        System.out.println("delere:" + new File("demo").delete());

        // 删除功能：我要删除test\\aaa这个文件夹
        File file2 = new File("test\\aaa");
        System.out.println("delete:" + file2.delete());

        // 删除功能：我要删除aaa文件夹
        //File file3 = new File("aaa");
        // System.out.println("delete:" + file5.delete());
        //要删除aaa文件夹，要注意aaa文件夹内不能包含文件或者文件夹

        //先删除aaa文件下得我文件或者文件夹
        File file4 = new File("aaa\\a.txt");
        //再删除该文件
        File file5 = new File("aaa");
        System.out.println("delete:" + file4.delete());
        System.out.println("delete:" + file5.delete());
    }
}
