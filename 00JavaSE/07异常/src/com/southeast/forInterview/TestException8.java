package com.southeast.forInterview;

/**
 *
 */
public class TestException8 {
    public static void main(String[] args) {
        System.out.println(getInt());
    }

    public static int getInt(){
        int a=10;
        try{
            System.out.println(a/0);
        }catch (ArithmeticException e){
            a=30;
            return a;
            /**
             * 执行到这里的时候 这里的 return a 就是 return 30了，形成返回路径
             * 再执行finnally语句，这样 a=40
             * 再次回到以前的返回路径。return 30
             */
        }finally {
            a=40;
        }
        return a;
    }
}
