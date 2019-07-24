package code_06_exception.catchException.tryCatchFinally;

/**
 * finally特点：
 * （1）被finally控制的语句体一定会执行
 * （2）在执行到finnaly之前，JVM退出了，就不能再执行了。
 *
 * try－catch-finally语句
 * try-catch语句还可以包括第三部分，就是finally子句。
 * 它表示无论是否出现异常，都应当执行的内容。
 *
 * 小结：
 *  try 块：用于捕获异常。
 *          其后可接零个或多个catch块，如果没有catch块，则必须跟一个finally块。
 *  catch 块：用于处理try捕获到的异常。
 *  finally 块：无论是否捕获或处理异常，finally块里的语句都会被执行。
 *          当在try块或catch块中遇到return语句时，finally语句块将在==方法返回之前==被执行。在以下4种特殊情况下，finally块不会被执行：
             1）在finally语句块中发生了异常。
             2）在前面的代码中用了System.exit()退出程序。
             3）程序所在的线程死亡。
             4）关闭CPU。
 */
public class Code_09_Exception {
    public static void main(String[] args) {
        int i = 0;
        String greetings[] = { " Hello world !", " Hello World !! ",
                " HELLO WORLD !!!" };
        while (i < 4) {
            try {
                // 特别注意循环控制变量i的设计，避免造成无限循环
                System.out.println(greetings[i++]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("数组下标越界异常");
            } finally {
                System.out.println("-----------finally---------------");
            }
        }
    }
}
