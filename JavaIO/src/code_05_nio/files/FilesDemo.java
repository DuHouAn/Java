package code_05_nio.files;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by 18351 on 2019/1/6.
 */
public class FilesDemo {
    public static void main(String[] args) {
        Path path = Paths.get("demo5.txt");
        //LinkOptions.NOFOLLOW_LINKS:表示检测时不包含符号链接文件。
        boolean isExist= Files.exists(path,new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
        System.out.println(isExist);
    }
}
