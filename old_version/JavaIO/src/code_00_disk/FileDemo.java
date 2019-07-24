package code_00_disk;

import java.io.File;

/**
 * File:文件和目录(文件夹)路径名的抽象表示形式
 * 构造方法：
 * 		File(String pathname)：根据一个路径得到File对象
 * 		File(String parent, String child):根据一个目录和一个子文件/目录得到File对象
 * 		File(File parent, String child):根据一个父File对象和一个子文件/目录得到File对象
 */
public class FileDemo {
    public static void main(String[] args) {
        //这三种方式创建的文件对象都相同
        //File(String pathname)：根据一个路径得到File对象
        File file=new File("demo\\a.txt");

        //File(String parent, String child):根据一个目录和一个子文件/目录得到File对象
        File file2=new File("demo","a.txt");

        //File(File parent, String child):根据一个父File对象和一个子文件/目录得到File对象
        File file3=new File(new File("demo"),"a.txt");
    }
}
