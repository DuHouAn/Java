package com.southeast.SystemStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *  System.in 标准输入流。是从键盘获取数据的
 *
 * 键盘录入数据：
 * 		A:main方法的args接收参数。
 * 			java HelloWorld hello world java
 * 		B:Scanner(JDK5以后的)
 * 			Scanner sc = new Scanner(System.in);
 * 			String s = sc.nextLine();
 * 			int x = sc.nextInt()
 * 		C:通过字符缓冲流包装标准输入流实现
 * 			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//字符缓冲流包装标准输入流
 */
public class SystemInDemo {
    public static void main(String[] args) throws IOException {
      //method();
        method2();
    }

    public static void method() {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入一个字符串：");
        String s = sc.nextLine();
        System.out.println("您输出的字符串是："+s);

        System.out.println("请输入一个整数：");
        int x = sc.nextInt();
        System.out.println("您输入的整数是："+x);
    }

    //通过字符缓冲流包装标准输入流实现
    //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//字符缓冲流包装标准输入流
    public static void method2() throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        System.out.println("请输入一个字符串：");
        String line = br.readLine();
        System.out.println("您输出的字符串是："+line);

        System.out.println("请输入一个整数：");
        String i=br.readLine();
        int ii=Integer.parseInt(i);
        System.out.println("您输入的整数是："+ii);
        br.close();
    }
}
