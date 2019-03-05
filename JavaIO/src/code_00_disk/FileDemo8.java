package code_00_disk;

import java.io.File;

/**
 * Created by 18351 on 2019/1/4.
 */
public class FileDemo8 {
    public static void main(String[] args) {
        //指定源目录
        File srcFolder = new File("demo1");
        //指定目标目录
        File destFolder = new File("demo2");

        File[] fileArray = srcFolder.listFiles();
        if(fileArray!=null){
            for (File file : fileArray) {
                File newFile=new File(destFolder,file.getName());
                file.renameTo(newFile);
            }
        }
    }
}