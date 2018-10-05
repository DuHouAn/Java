package com.southeast.StringBuffer.test7;

/**
 * Created by 18351 on 2018/8/31.
 */
public class StringBufferTest {
    public static void main(String[] args) {
        String s="hello";
        System.out.println(stringToStringBuffer(s));
        System.out.println(stringBufferToString(stringToStringBuffer(s)));
    }

    /**
     * String和StringBuffer的相互转换
     *  之 String -->StringBuffer
     */
    public static StringBuffer stringToStringBuffer(String s) {
        // 注意：不能把字符串的值直接赋值给StringBuffer
        // StringBuffer sb = "hello";
        // StringBuffer sb = s;

        // 方式1:通过构造方法
      /*  StringBuffer sb = new StringBuffer(s);
        return sb;*/
        // 方式2：通过append()方法
        StringBuffer sb2 = new StringBuffer();
        sb2.append(s);
        return sb2;
    }

    /**
     * String和StringBuffer的相互转换
     *  之 StringBuffer -->String
     */
    public static String stringBufferToString(StringBuffer buffer){
        // 方式1:通过构造方法
       /* String str = new String(buffer);
        return str;*/
        // 方式2：通过toString()方法
        String str2 = buffer.toString();
        return str2;
    }


}
