package com.southeast.BigInteger.test1;

import java.math.BigInteger;

/**
 * * BigInteger:可以让超过Integer范围内的数据进行运算
 *
 * 构造方法：
 * BigInteger(String val)
 */
public class BigIntegerDemo {
    public static void main(String[] args) {
        // 这几个测试，是为了简单超过int范围内，Integer就不能再表示，所以就更谈不上计算了。
        Integer i = new Integer(100);
        System.out.println(i);
        System.out.println(Integer.MAX_VALUE);
        Integer ii = new Integer("2147483647");
        System.out.println(ii);

        // // NumberFormatException
        // Integer iii = new Integer("2147483648");
        // System.out.println(iii);

        // 通过大整数来创建对象
        BigInteger bi = new BigInteger("2147483648");
        System.out.println("bi:" + bi);
    }
}
