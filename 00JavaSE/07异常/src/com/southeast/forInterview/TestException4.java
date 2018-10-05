package com.southeast.forInterview;

/**
 * Created by 18351 on 2018/8/29.
 */
public class TestException4 {
    public static void main(String[] args) {
        try{
            bar();
        }catch(NullPointerException e){
            //NullPointerException是不受检查异常
            //我们能捕获到一般异常或者是不被检查的异常，即使在throws语句中没被提及。
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        foo(); //不受检查的异常在程序中不要求被处理或用throws语句告知。
    }

    public static void bar(){

    }

    public static void foo() throws NullPointerException{
    }
}
