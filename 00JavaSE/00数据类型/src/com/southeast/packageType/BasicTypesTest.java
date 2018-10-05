package com.southeast.packageType;

public class BasicTypesTest {
    public static void main(String[] args) {
        /**
         * 八个基本类型：
         * boolean/1
         * byte/8
         * char/16
         * short/16
         * int/32
         * float/32
         * long/64
         * double/64
         */
        //基本类型都有对应的包装类型，基本类型与其对应的包装类型之间的赋值使用自动 装箱与拆箱完成
        Integer x = 2; // 装箱
        int y = x; // 拆箱

        System.out.println(y==x);
    }
}
