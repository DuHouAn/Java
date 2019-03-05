package code_06_exception.catchException.tryCatch;

/**
 *
 * 注意：
 * 若程序不发生异常，我们未做处理，JVM会做默认处理：
 *      （1）将异常名称、原因及出现的问题输出到控制台
 *      （2）同时会结束程序
 *
 *例3  不捕捉、也不声明抛出运行时异常。
 *由于检查运行时异常的代价远大于捕捉异常所带来的益处，运行时异常不可查。
 * TODO:Java编译器允许忽略运行时异常，一个方法可以既不捕捉，也不声明抛出运行时异常。
 */
public class Code_02_Exception {
    public static void main(String[] args) {
       int a=1;
       int b=0;
       System.out.println("a/b的值是："+a/b);
        System.out.println("over");
    }
}
