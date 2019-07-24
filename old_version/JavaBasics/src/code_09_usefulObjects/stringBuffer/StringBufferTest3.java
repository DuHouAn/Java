package code_09_usefulObjects.stringBuffer;

/**
 * Created by 18351 on 2018/12/26.
 */
public class StringBufferTest3 {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "world";
        System.out.println(s1 + "---" + s2);
        change(s1, s2);
        System.out.println(s1 + "---" + s2);

        StringBuffer sb1 = new StringBuffer("hello");
        StringBuffer sb2 = new StringBuffer("world");
        System.out.println(sb1 + "---" + sb2);
        change(sb1, sb2);
        System.out.println(sb1 + "---" + sb2);
    }

    //StringBuffer作为参数
    public static void change(StringBuffer sb1, StringBuffer sb2) {
        sb1 = sb2;
        sb2.append(sb1);
    }

    //String作为参数传递，效果和基本类型作为参数传递是一样的。
    public static void change(String s1, String s2) {
        s1 = s2;
        s2 = s1 + s2;
    }
}