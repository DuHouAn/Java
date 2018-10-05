package com.southeast.String.test6;

import java.util.Scanner;

/**
 * String的转换功能：
 * byte[] getBytes():把字符串转换为字节数组。
 * char[] toCharArray():把字符串转换为字符数组。
 * static String valueOf(char[] chs):把字符数组转成字符串。
 * static String valueOf(int i):把int类型的数据转成字符串。
 * 		注意：String类的valueOf方法可以把任意类型的数据转成字符串。
 * String toLowerCase():把字符串转成小写。
 * String toUpperCase():把字符串转成大写。
 * String concat(String str):把字符串拼接。
 */
public class StringDemo {
    public static void main(String[] args) {
        //test();
        test2();
    }


    /**
     * String的转换功能
     */
    public static void test(){
        // 定义一个字符串对象
        String s = "JavaSE";

        // byte[] getBytes():把字符串转换为字节数组。
        byte[] bys = s.getBytes();
        for (int x = 0; x < bys.length; x++) {
            System.out.println(bys[x]);//74 97 118 97 83 69
        }
        System.out.println("----------------");

        // char[] toCharArray():把字符串转换为字符数组。
        char[] chs = s.toCharArray();
        for (int x = 0; x < chs.length; x++) {
            System.out.println(chs[x]);//J a v a S E
        }
        System.out.println("----------------");

        // static String valueOf(char[] chs):把字符数组转成字符串。
        String ss = String.valueOf(chs);
        System.out.println(ss);//JavaSE
        System.out.println("----------------");

        // static String valueOf(int i):把int类型的数据转成字符串。
        int i = 100;
        String sss = String.valueOf(i);
        System.out.println(sss);//100 是字符串
        System.out.println("----------------");

        // String toLowerCase():把字符串转成小写。
        System.out.println("toLowerCase:" + s.toLowerCase());//javase
        System.out.println("s:" + s);
        // System.out.println("----------------");
        // String toUpperCase():把字符串转成大写。
        System.out.println("toUpperCase:" + s.toUpperCase());//JAVASE
        System.out.println("----------------");

        // String concat(String str):把字符串拼接。
        String s1 = "hello";
        String s2 = "world";
        String s3 = s1 + s2;
        String s4 = s1.concat(s2);
        System.out.println("s3:"+s3);//helloworld
        System.out.println("s4:"+s4);//helloworld
    }

    /**
     * 需求：把一个字符串的首字母转成大写，其余为小写。(只考虑英文大小写字母字符)
     * 举例：
     * 		helloWORLD
     * 结果：
     * 		Helloworld
     *
     * 		A:先获取第一个字符
     * 		B:获取除了第一个字符以外的字符
     * 		C:把A转成大写
     * 		D:把B转成小写
     * 		E:C拼接D
     */
    public static void test2(){
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();

        //String substring(int start,int end):从指定位置开始到指定位置结束截取字符串。左闭右开
     /*   String str1=str.substring(0,1);
        String str2=str.substring(1);

        str1=str1.toUpperCase();
        str2=str2.toLowerCase();

        String str3=str1.concat(str2);
        System.out.println(str3);*/

        //链式编程
        String str2=str.substring(0,1).toUpperCase().
                concat(str.substring(1).toLowerCase());
        System.out.println(str2);
    }
}
