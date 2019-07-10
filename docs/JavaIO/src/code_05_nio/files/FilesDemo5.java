package code_05_nio.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by 18351 on 2019/1/6.
 */
public class FilesDemo5 {
    public static void main(String[] args) throws IOException {
        Path path= Paths.get("demo7.txt");

        System.out.println(Files.getLastModifiedTime(path));
        System.out.println(Files.size(path));
        System.out.println(Files.isSymbolicLink(path));
        System.out.println(Files.isDirectory(path));
        System.out.println(Files.readAttributes(path,"*"));
    }
}
