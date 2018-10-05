package com.southeast.StringBuffer.test6;

/**
 * * StringBuffer的截取功能:注意返回值类型不再是StringBuffer本身了
 * public String substring(int start) //TODO：注意截取返回的是String,而不是StringBuffer了
 * public String substring(int start,int end)
 */
public class StringBufferDemo {
    public static void main(String[] args) {
        // 创建字符串缓冲区对象
        StringBuffer sb = new StringBuffer();

        // 添加元素
        sb.append("hello").append("world").append("java");
        System.out.println("sb:" + sb);//sb:helloworldjava

        // 截取功能
        // public String substring(int start)
        String s = sb.substring(5);
        System.out.println("s:" + s);//s:worldjava
        System.out.println("sb:" + sb);//sb:helloworldjava

        // public String substring(int start,int end)
        String ss = sb.substring(5, 10);
        System.out.println("ss:" + ss);//ss:world
        System.out.println("sb:" + sb);//sb:helloworldjava
    }
}
