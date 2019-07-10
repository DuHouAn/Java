package code_00_disk;

import java.io.File;
import java.io.IOException;

/**
 * public boolean createNewFile() //创建文件 如果存在这样的文件，就不创建了
 * public boolean mkdir()  //创建文件夹 如果存在这样的文件夹，就不创建了
 * public boolean mkdirs() //创建文件夹,如果父文件夹不存在，会帮你创建出来
 */
public class FileDemo2 {
    public static void main(String[] args) throws IOException {
        // 需求：在当前目录下创建一个文件夹demo
        File file = new File("demo");
        System.out.println("mkdir:" + file.mkdir());

        // 需求:我要在当前目录demo下创建一个文件a.txt
        File file2 = new File("demo\\a.txt");
        System.out.println("createNewFile:" + file2.createNewFile());

        // 需求：我要在目录test下创建一个文件b.txt
        // Exception in thread "main" java.io.IOException: 系统找不到指定的路径。
        // 注意：要想在某个目录下创建内容，该目录首先必须存在。
        // File file3 = new File("test\\b.txt");
        // System.out.println("createNewFile:" + file3.createNewFile());

        // 需求:我要在目录test下创建aaa目录
        // File file4 = new File("test\\aaa");
        // System.out.println("mkdir:" + file4.mkdir());


        //先创建test目录，然后再在test目录下创建aaa
        // File file5 = new File("test");
        // File file6 = new File("test\\aaa");
        // System.out.println("mkdir:" + file5.mkdir());
        // System.out.println("mkdir:" + file6.mkdir());

        // 其实我们有更简单的方法
        File file7 = new File("test\\aaa");
        System.out.println("mkdirs:" + file7.mkdirs());

        // 看下面的这个东西：
        File file8 = new File("aaa\\a.txt");
        System.out.println("mkdirs:" + file8.mkdirs());
        //骑白马的不一定是王子，可能是唐僧。
        // 注意：创建文件还是文件夹，方法不要调错了。
    }
}
