package code_05_nio.files;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by 18351 on 2019/1/6.
 */
public class FilesDemo6 {
    public static void main(String[] args) throws IOException {
        Path path= Paths.get("demo3\\demo2");

        DirectoryStream<Path> paths=Files.newDirectoryStream(path);
        for(Path p:paths){
            System.out.println(p.getFileName());
        }
    }
}