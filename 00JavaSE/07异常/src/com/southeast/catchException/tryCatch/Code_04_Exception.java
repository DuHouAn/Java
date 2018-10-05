package com.southeast.catchException.tryCatch;

/**
 * JFK1.7的新特性：
 *  try{
 *
 *  }catch(异常1 | 异常2 | 异常3 。。。){ //多个异常一起处理
 *
 *  }
 *
 * 不足：
 * （1）处理方式是一致的
 * （2）多个异常必须是平级关系
 *
 */
public class Code_04_Exception {
    public static void main(String[] args) {
        method();
    }

    public static void method() {
        int a = 10;
        int b = 0;
        int[] arr = { 1, 2, 3 };

        // try {
        // System.out.println(a / b);
        // System.out.println(arr[3]);
        // System.out.println("这里出现了一个异常，你不太清楚是谁，该怎么办呢?");
        // } catch (ArithmeticException e) {
        // System.out.println("除数不能为0");
        // } catch (ArrayIndexOutOfBoundsException e) {
        // System.out.println("你访问了不该的访问的索引");
        // } catch (Exception e) {
        // System.out.println("出问题了");
        // }

        // JDK7的处理方案
        try {
            System.out.println(a / b);
            System.out.println(arr[3]);
        } catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
            System.out.println("出问题了");
        }

        System.out.println("over");
    }
}
