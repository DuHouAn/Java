package code_05_nio.path;

import java.io.File;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by 18351 on 2019/1/6.
 */
public class PathDemo2 {
    public static void main(String[] args) {
        Path path=Paths.get("demo5.txt");
        File file=path.toFile();
        URI uri=path.toUri();
        System.out.println(path);
        System.out.println(file);
        System.out.println(uri);
    }
}
