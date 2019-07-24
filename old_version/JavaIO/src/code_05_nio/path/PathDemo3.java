package code_05_nio.path;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by 18351 on 2019/1/6.
 */
public class PathDemo3 {
    public static void main(String[] args) {
        Path path= Paths.get("demo3\\test3.txt");
        System.out.println("文件名："+ path.getFileName());
        System.out.println("名称元素的数量："+path.getNameCount());
        System.out.println("父路径："+ path.getParent());
        System.out.println("根路径："+ path.getRoot());
        System.out.println("是否是绝对路径:"+path.isAbsolute());

        //startWith() 参数既可以是字符串，也可以是Path
        System.out.println("是否是以路径demo3开头："+path.startsWith(Paths.get("demo3")));
        System.out.println("该路径的字符串形式："+path.toString());
    }
}
