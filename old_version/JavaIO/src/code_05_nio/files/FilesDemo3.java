package code_05_nio.files;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by 18351 on 2019/1/6.
 */
public class FilesDemo3 {
    public static void main(String[] args) throws IOException {
        Path path= Paths.get("demo7.txt");
        Files.delete(path);
    }
}
