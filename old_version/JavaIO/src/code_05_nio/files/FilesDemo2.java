package code_05_nio.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by 18351 on 2019/1/6.
 */
public class FilesDemo2 {
    public static void main(String[] args) throws IOException {
        Path path= Paths.get("demo7.txt");
        if(!Files.exists(path)){
            Files.createFile(path);
        }

        Path path2=Paths.get("demo4");
        if(!Files.exists(path2)){
            Files.createDirectory(path2);
        }

        Path path3=Paths.get("demo5\\test");
        if(!Files.exists(path3)){
            Files.createDirectories(path3);
        }
    }
}
