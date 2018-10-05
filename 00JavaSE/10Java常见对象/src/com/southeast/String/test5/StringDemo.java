package com.southeast.String.test5;

import java.util.Scanner;

/**
 *  String类的获取功能
 * int length():获取字符串的长度。
 * char charAt(int index):获取指定索引位置的字符
 * int indexOf(int ch):返回指定字符在此字符串中第一次出现处的索引。
 * 		为什么这里是int类型，而不是char类型?
 * 		原因是：'a'和97其实都可以代表'a'
 * int indexOf(String str):返回指定字符串在此字符串中第一次出现处的索引。
 * int indexOf(int ch,int fromIndex):返回指定字符在此字符串中从指定位置后第一次出现处的索引。
 * int indexOf(String str,int fromIndex):返回指定字符串在此字符串中从指定位置后第一次出现处的索引。
 * String substring(int start):从指定位置开始截取字符串,默认到末尾。
 * String substring(int start,int end):从指定位置开始到指定位置结束截取字符串。
 */
public class StringDemo {
    public static void main(String[] args) {
        //test();
        //test2();
    }

    /**
     * String类的获取功能
     */
    public static void test(){
        // 定义一个字符串对象
        String s = "helloworld";

        // int length():获取字符串的长度。
        System.out.println("s.length:" + s.length());//10
        System.out.println("----------------------");

        // char charAt(int index):获取指定索引位置的字符
        System.out.println("charAt:" + s.charAt(7));//r
        System.out.println("----------------------");

        // int indexOf(int ch):返回指定字符在此字符串中第一次出现处的索引。
        System.out.println("indexOf:" + s.indexOf('l'));//2
        System.out.println("----------------------");

        // int indexOf(String str):返回指定字符串在此字符串中第一次出现处的索引。
        System.out.println("indexOf:" + s.indexOf("owo"));//4
        System.out.println("----------------------");

        // int indexOf(int ch,int fromIndex):返回指定字符在此字符串中从指定位置后第一次出现处的索引。
        System.out.println("indexOf:" + s.indexOf('l', 4));//8
        System.out.println("indexOf:" + s.indexOf('k', 4)); // -1
        System.out.println("indexOf:" + s.indexOf('l', 40)); // -1
        System.out.println("----------------------");

        // int indexOf(String str,intfromIndex):返回指定字符串在此字符串中从指定位置后第一次出现处的索引。
        System.out.println("indexOf:" + s.indexOf("owo", 4));//4
        System.out.println("indexOf:" + s.indexOf("ll", 4)); //-1
        System.out.println("indexOf:" + s.indexOf("ld", 40)); // -1
        System.out.println("----------------------");

        // String substring(int start):从指定位置开始截取字符串,默认到末尾。包含start这个索引
        System.out.println("substring:" + s.substring(5));//world
        System.out.println("substring:" + s.substring(0));//helloworld
        System.out.println("----------------------");

        // String substring(int start,int
        // end):从指定位置开始到指定位置结束截取字符串。包括start索引但是不包end索引
        System.out.println("substring:" + s.substring(3, 8));//lowor
        System.out.println("substring:" + s.substring(0, s.length()));//helloworld
    }

    /**
     * 获取 字符串中的每个字符
     */
    public static void test2(){
        String s="helloworld";
        for(int i=0;i<s.length();i++){
            System.out.println(s.charAt(i));
        }
    }
}
