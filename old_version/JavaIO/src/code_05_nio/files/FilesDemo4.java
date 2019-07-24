package code_05_nio.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by 18351 on 2019/1/6.
 */
public class FilesDemo4 {
    public static void main(String[] args) throws IOException {
        Path srcPath= Paths.get("demo6.txt");
        Path destPath=Paths.get("demo7.txt");

        //Files.copy(srcPath,destPath);

        //强制覆盖已经存在的目标文件
        Files.copy(srcPath,destPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
