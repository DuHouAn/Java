package code_06_exception.catchException.tryCatch;

/**
 * throw关键字
 * throw:如果出现了异常情况，我们可以把该异常抛出，这个时候的抛出的应该是异常的对象。
 *
 * TODO:throws和throw的区别(面试题)
 * throws
 * (1)用在方法声明后面，跟的是异常类名
 * (2)可以跟多个异常类名，用逗号隔开
 * (3)表示抛出异常，由该方法的调用者来处理
 * (4)throws表示出现异常的一种可能性，并不一定会发生这些异常
 throw
 * (1)用在方法体内，跟的是异常对象名
 * (2)只能抛出一个异常对象名
 * (3)表示抛出异常，由方法体内的语句处理
 * (4)throw则是抛出了异常，执行throw则一定抛出了某种异常
/**
 * 处理异常原则：
 * 该功能内部可将问题处理，用try,如果处理不了，就交由调用者处理，用throws
 * 区别：要使得后续程序继续执行需要使用try
 */
public class Code_08_Exception {
    public static void main(String[] args) {
        method();

        try {
            method2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void method() {
        int a = 10;
        int b = 0;
        if (b == 0) {
            throw new ArithmeticException(); //抛出的是运行时异常对象，方法可不抛出异常
        } else {
            System.out.println(a / b);
        }
    }

    public static void method2() throws Exception {
        //这里抛出Exception,要交给该方法的调用者处理
        int a = 10;
        int b = 0;
        if (b == 0) {
            throw new Exception();//这里抛出的是编译期异常，方法必须要抛出异常。
        } else {
            System.out.println(a / b);
        }
    }

}
