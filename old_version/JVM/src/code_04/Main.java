package code_04;

/**
 * Created by DHA on 2019/1/8.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        //loader 中构造函数参数是 .class文件的根目录
        FileSystemClassLoader loader=new FileSystemClassLoader
                ("F:\\Java_Review\\Java\\JVM\\out\\production\\JVM");
        //类的名称是全路径名称（带包的）
        Class clazz=loader.findClass("code_00.IntegerDemo");
        System.out.println(clazz.getName());
    }
}
