package code_05_nio.path;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by 18351 on 2019/1/6.
 */
public class PathDemo5 {
    public static void main(String[] args) throws IOException {
        Path path= Paths.get("../JavaIO");

        System.out.println("original ："+ path.toAbsolutePath());
        System.out.println("after normalize:"+ path.toAbsolutePath().normalize());
        System.out.println("after toRealPath:"+ path.toRealPath());
    }
}
