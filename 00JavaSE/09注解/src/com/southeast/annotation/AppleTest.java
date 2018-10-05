package com.southeast.annotation;

public class AppleTest {
    public static void main(String[] args) {
        //方式一:
        Apple apple=new Apple();
        FruitInfoUtil.getFruitInfo(apple.getClass()); //直接通过反射得到注解中的值

        //这种访问 是获取不了竹节虫中的值的
     /*   System.out.println(apple.getAppleName()); //null
        System.out.println(apple.getAppleColor());//null
        System.out.println(apple.getAppleProvider());//null*/

        //方式二：
        FruitInfoUtil.getFruitInfo(Apple.class);
    }
}
