package code_00_disk;

import java.io.File;

/**
 * Created by 18351 on 2019/1/4.
 */
public class FileDemo7 {
    public static void main(String[] args) {
        // 指定一个目录
        File file = new File("src\\code_00_disk");

        // public String[] list():获取指定目录下的所有文件或者文件夹的名称数组
        String[] strArray = file.list();
        for (String s : strArray) {
            System.out.println(s);
        }
        System.out.println("------------");

        // public File[] listFiles():获取指定目录下的所有文件或者文件夹的File数组
        File[] fileArray = file.listFiles();
        if(fileArray!=null){
            for (File f : fileArray) {
                System.out.println(f.getName());
            }
        }
        System.out.println("--------------");

        listAllFiles(file);
    }

    //递归地列出一个目录下所有文件
    public static void listAllFiles(File dir) {
        if (dir == null || !dir.exists()) {
            return;
        }
        if (dir.isFile()) {
            System.out.println(dir.getName());
            return;
        }
        for (File file : dir.listFiles()) {
            listAllFiles(file);
        }
    }
}