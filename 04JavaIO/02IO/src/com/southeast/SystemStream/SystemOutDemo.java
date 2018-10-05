package com.southeast.SystemStream;

import java.io.PrintStream;

/**
* 标准输入输出流
* System类中的两个成员变量：
*		public static final InputStream in “标准”输入流。
* 		public static final PrintStream out “标准”输出流。
*
* 		InputStream is = System.in;
* 		PrintStream ps = System.out; //字节流打印流	PrintStream
 */
public class SystemOutDemo {
    public static void main(String[] args) {
        // 有这里的讲解我们就知道了，这个输出语句其本质是IO流操作，把数据输出到控制台。
        System.out.println("helloworld");

        PrintStream ps=System.out; //获取标准输出流
        ps.println("helloworld");
        ps.print("123");
        ps.println();//该方法可以实现换行
        //ps.print(); 这个方法是不存在的,因为没有意义
    }
}
