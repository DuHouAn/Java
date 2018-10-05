package com.southeast.StringBuffer.test7;

/**
 *
 * 把字符串反转
 */
public class StringBufferTest3 {
    public static void main(String[] args) {
       myReverseString("abc");
       myReverseString2("abc");
    }

    //将字符串转化为字符数组，倒叙拼接，然后返回
    public static void myReverseString(String s){
        String str="";
        char[] chs=s.toCharArray();

        for(int index=chs.length-1;index>=0;index--){
            str+=chs[index];
        }
        System.out.println(str);
    }

    //通过StringBuffer的reverse()方法
    public static void myReverseString2(String s){
        StringBuffer sb=new StringBuffer(s);
        String str=sb.reverse().toString();
        System.out.println(str);
    }
}
