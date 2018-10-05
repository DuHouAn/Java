package com.southeast.String.test8;

/**
 *
 * 字符串反转
 * 举例：键盘录入”abc”
 * 输出结果：”cba”
 *
 * 分析：
 * 		A:键盘录入一个字符串
 * 		B:定义一个新字符串
 * 		C:倒着遍历字符串，得到每一个字符
 * 			a:length()和charAt()结合
 * 			b:把字符串转成字符数组
 * 		D:用新字符串把每一个字符拼接起来
 * 		E:输出新串
 */

import java.util.Scanner;
public class StringTest2 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        String reverseStr=myReverse(str);
        System.out.println(reverseStr);
    }

    public static String myReverse(String str) {
        //把字符串转成字符数组
        char[] chs=str.toCharArray();

        String res="";
        for(int index=chs.length-1;index>=0;index--){
            res+=chs[index];
        }
        return res;
    }
}
