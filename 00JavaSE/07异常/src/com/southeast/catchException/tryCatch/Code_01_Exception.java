package com.southeast.catchException.tryCatch;

/**
 * 例2
 * 捕捉运行时系统自动抛出“除数为0”引发的ArithmeticException异常。
 *  事实上，“除数为0”等ArithmeticException，是RuntimException的子类。
 *  而运行时异常将由运行时系统自动抛出，不需要使用throw语句。
 *
 *  注意：
 *   try代码块里面的代码越少越好
 */
public class Code_01_Exception {
    public static void main(String[] args) {
        int a = 1;
        int b = 0;
        try { // try监控区域
            //if (b == 0) throw new ArithmeticException();
            //TODO：运行时异常将由运行时系统自动抛出，不需要使用throw语句。
            System.out.println("a/b的值是：" + a / b);
            System.out.println("this will not be printed!");
        }
        catch (ArithmeticException e) { // catch捕捉异常
            System.out.println("程序出现异常，变量b不能为0！");
        }
        System.out.println("程序正常结束。");
    }
}
