package com.southeast.SystemStream;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

/**
* 标准输入输出流
* System类中的两个成员变量：
*		public static final InputStream in “标准”输入流。
* 		public static final PrintStream out “标准”输出流。
*
* 		InputStream is = System.in;
* 		PrintStream ps = System.out; //字节流打印流	PrintStream
 *
 需求：使用标准输入流的方式一样把数据输出到控制台
 */
public class SystemOutDemo2 {
    public static void main(String[] args) throws IOException {
        //利用转换流，也可以实现数据输出到控制台
        BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(System.out));

        bw.write("hello");
        bw.newLine();
        bw.write("world");
        bw.newLine();
        bw.write("java");
        bw.newLine();
        bw.flush();

        bw.close();
    }
}
