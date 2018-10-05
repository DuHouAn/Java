package com.southeast.Integer.test2;

/**
 *  Integer的构造方法：
 * public Integer(int value)
 * public Integer(String s)
 * 		注意：这个字符串必须是由数字字符组成
 */
public class IntegerDemo {
    public static void main(String[] args) {
        // 方式1
        int i = 100;
        Integer ii = new Integer(i);
        System.out.println("ii:" + ii);

        // 方式2
        // NumberFormatException
        // String s = "abc";
        String s = "100";
        Integer iii = new Integer(s);
        System.out.println("iii:" + iii);
    }
}
