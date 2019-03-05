package code_06_exception.catchException.tryCatch;

/**
 *
 * Error(严重的问题)：比如OutOfMemory
 * Exception:
 *      |--编译器异常：不是RuntimeException的异常，不处理，编译就不能通过
 *      |--运行期异常：RuntimeException,可以不处理，因为是自身的代码不够严谨
 *
 * 例1 算术异常
 * 匹配的原则是：
 * 如果抛出的异常对象属于catch子句的异常类，
 * 或者属于该异常类的子类，则认为生成的异常对象与catch块捕获的异常类型相匹配。
 */
public class Code_00_Exception {
    public static void main(String[] args) {
        int a = 1;
        int b = 0;
        try { // try监控区域
            if (b == 0) {
                throw new ArithmeticException(); // 通过throw语句抛出异常
            }
            System.out.println("a/b的值是：" + a / b);
            System.out.println("this will not be printed!");
        } catch (ArithmeticException e) { // catch捕捉异常
            System.out.println("程序出现异常，变量b不能为0！");
        }
        System.out.println("程序正常结束。");
    }
}
