package code_05_nio.path;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by 18351 on 2019/1/6.
 */
public class PathDemo {
    public static void main(String[] args) {
        //方式一
        Path path=Paths.get("demo5.txt");
        System.out.println(path);

        //方式二
        Path path2 = FileSystems.getDefault().getPath("demo5.txt");
        System.out.println(path2);
    }
}
