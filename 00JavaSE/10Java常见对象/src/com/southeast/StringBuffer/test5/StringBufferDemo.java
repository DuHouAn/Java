package com.southeast.StringBuffer.test5;

/**
 *StringBuffer的反转功能：
 * public StringBuffer reverse()
 */
public class StringBufferDemo {
    public static void main(String[] args) {
        // 创建字符串缓冲区对象
        StringBuffer sb = new StringBuffer();

        // 添加数据
        sb.append("霞青林爱我");
        System.out.println("sb:" + sb);

        // public StringBuffer reverse()
        sb.reverse();
        System.out.println("sb:" + sb);
    }
}
