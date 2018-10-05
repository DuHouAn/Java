package com.southeast.catchException.tryCatchFinally;

/**
 *
 * try、catch、finally语句块的执行顺序:
 1)当try没有捕获到异常时：
        try语句块中的语句逐一被执行，程序将跳过catch语句块，
        执行finally语句块和其后的语句；
 2)当try捕获到异常，catch语句块里没有处理此异常的情况：
        当try语句块里的某条语句出现异常时，而没有处理此异常的catch语句块时，此异常将会抛给JVM处理，
        finally语句块里的语句还是会被执行，但finally语句块后的语句不会被执行；
 3)当try捕获到异常，catch语句块里有处理此异常的情况：
        在try语句块中是按照顺序来执行的，当执行到某一条语句出现异常时，程序将跳到catch语句块，
        并与catch语句块逐一匹配，找到与之对应的处理程序，其他的catch语句块将不会被执行，
        而try语句块中，出现异常之后的语句也不会被执行，catch语句块执行完后，
        执行finally语句块里的语句，
        最后执行finally语句块后的语句；
 见tryCatchFinally语句块的执行.png
 */

/**
 * final和finally和finalize的区别？
 *  final：最终的意思，可以修饰类，修饰成员变量，修饰成员方法
 *      修饰类：类不能被继承
 *      修饰变量：变量是常量
 *      修饰方法：方法不能被重写（Override）
 *  finally:是异常处理的关键字，用于释放资源。
 *      一般来说，代码必须执行（特殊情况：在执行到finally JVM就退出了）
 *
 *  finalize:是Object的一个方法，用于垃圾回收。
 */
public class Code_11_Exception {
    public static void main(String[] args) {

    }
}
