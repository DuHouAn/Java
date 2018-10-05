package com.southeast.StringBuffer.test3;

/**
 * StringBuffer的删除功能
 *  public StringBuffer deleteCharAt(int index):
 *      删除指定位置的字符，并返回本身
 *  public StringBuffer delete(int start,int end):
 *      删除从指定位置开始指定位置结束的内容，并返回本身
 */
public class StringBufferDemo {
    public static void main(String[] args) {
        // 创建对象
        StringBuffer sb = new StringBuffer();

        // 添加功能
        sb.append("hello").append("world").append("java");

        // public StringBuffer deleteCharAt(int index):删除指定位置的字符，并返回本身
        // 需求：我要删除e这个字符，肿么办?
        // sb.deleteCharAt(1);
        // 需求:我要删除第一个l这个字符，肿么办?
        // sb.deleteCharAt(1);

        // public StringBuffer delete(int start,int end):删除从指定位置开始指定位置结束的内容，并返回本身
        // 需求：我要删除world这个字符串，肿么办?
        //sb.delete(5, 10);
        //需求:删除所有字符
        System.out.println("sb;"+sb);
        sb.delete(0,sb.length());
        System.out.println("sb:" + sb);
    }
}
