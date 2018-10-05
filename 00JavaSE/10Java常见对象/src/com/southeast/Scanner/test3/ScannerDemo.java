package com.southeast.Scanner.test3;

import java.util.Scanner;

/**
 *常用的两个方法：
 * 		public int nextInt():获取一个int类型的值
 * 		public String nextLine():获取一个String类型的值
 *
 * 出现问题了：
 * 		先获取一个数值，在获取一个字符串，会出现问题。
 * 		主要原因：就是那个换行符号的问题。
 *
 * 如何解决呢?
 * 		A:先获取一个数值后，在创建一个新的键盘录入对象获取字符串。
 * 		B:把所有的数据都先按照字符串获取，然后要什么，你就对应的转换为什么。
 */
public class ScannerDemo {
    public static void main(String[] args) {
   /*     Scanner sc=new Scanner(System.in);

        int x=sc.nextInt();
        System.out.println("x:"+x);

        String line=sc.nextLine(); //这里会有问题 因为会将换行符当作字符输输入
        System.out.println("line:"+line);*/

        //method();
        method2();
    }

    //A:先获取一个数值后，再创建一个新的键盘录入对象获取字符串。
    public static void method(){
        Scanner sc=new Scanner(System.in);
        int x=sc.nextInt();
        System.out.println("x:"+x);

        Scanner sc2=new Scanner(System.in);
        String line=sc2.nextLine(); //这里会有问题 因为会将换行符当作字符输输入
        System.out.println("line:"+line);
    }

    //B:把所有的数据都先按照字符串获取，然后要什么，你就对应的转换为什么。-->这个方法好
    public static void method2(){
        Scanner sc=new Scanner(System.in);

        String numStr1=sc.nextLine();
        String numStr2=sc.nextLine();

        int num1=Integer.parseInt(numStr1);
        int num2=Integer.parseInt(numStr2);

        int num3=num1+num2;
        System.out.println("num3="+num3);
    }
}
