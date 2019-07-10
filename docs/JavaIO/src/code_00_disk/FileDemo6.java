package code_00_disk;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 18351 on 2019/1/4.
 */
public class FileDemo6 {
    public static void main(String[] args) {
        // 创建文件对象
        File file = new File("demo2\\a.txt");

        System.out.println("getAbsolutePath:" + file.getAbsolutePath());
        System.out.println("getPath:" + file.getPath());
        System.out.println("getName:" + file.getName());
        System.out.println("length:" + file.length());
        System.out.println("lastModified:" + file.lastModified());//lastModified:1546584213001

        Date d = new Date(1546584213001L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(d);
        System.out.println(s);
    }
}
