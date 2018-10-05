package com.southeast.String.test8;

import java.util.Scanner;

/**
 * 需求：统计一个字符串中 大写字母字符 ，小写字母字符，数字字符出现的次数。(不考虑其他字符)
 *
 * 分析：
 * 		前提：字符串要存在
 * 		A:定义三个统计变量
 * 			bigCount=0
 * 			smallCount=0
 * 			numberCount=0
 * 		B:遍历字符串，得到每一个字符。
 * 			length()和charAt()结合
 * 		C:判断该字符到底是属于那种类型的
 * 			大：bigCount++
 * 			小：smallCount++
 * 			数字：numberCount++
 *
 * 			这道题目的难点就是如何判断某个字符是大的，还是小的，还是数字的。
 * 			ASCII码表：
 * 				0	48
 * 				A	65
 * 				a	97
 * 			虽然，我们按照数字的这种比较是可以的，但是想多了，有比这还简单的
 * 				char ch = s.charAt(x);
 *
 * 				if(ch>='0' && ch<='9') numberCount++
 * 				if(ch>='a' && ch<='z') smallCount++
 * 				if(ch>='A' && ch<='Z') bigCount++
 *		D:输出结果。
 */
public class StringTest4 {
    public static void main(String[] args) {
        printCount();
    }

    public static void printCount() {
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();

        int numberCount=0;
        int lowercaseCount=0;
        int upercaseCount=0;

        for(int index=0;index<str.length();index++){
            char ch=str.charAt(index);
            if(ch>='0' && ch<='9'){
                numberCount++;
            }else if(ch>='a' && ch<='z'){
                lowercaseCount++;
            }else if(ch>='A' && ch<='Z'){
                upercaseCount++;
            }
        }
        System.out.println("数字有"+numberCount+"个");
        System.out.println("小写字母有"+lowercaseCount+"个");
        System.out.println("大写字母有"+upercaseCount+"个");
    }
}
