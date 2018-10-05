package com.southeast.String.test2;

public class StringDemo {
    public static void main(String[] args) {
        //test();
        //test2();
        //test3();
        test4();
    }

    /**
     * 字符串的特点：一旦被赋值，就不能改变
     */
    public static void test() {
        String s = "hello";
        s += "world";
        System.out.println("s:" + s); // helloworld
    }

    /**
     *
     * String s = new String(“hello”)和String s = “hello”;的区别?
     * 有。前者会创建2个对象，后者创建1个对象。
     *
     * ==和qeuals的区别
     * TODO：==:比较引用类型，比较的是地址值是否相同
     * TODO：equals:比较引用类型，默认也是比较地址值是否相同，
     * String类重写了equals()方法，比较的是内容是否相同。
     */
    public static void test2() {
        String s1 = new String("hello");
        String s2 = "hello";

        System.out.println(s1 == s2);// false
        System.out.println(s1.equals(s2));// true
    }

    /**
     * 看程序，写结果
     */
    public static void test3(){
        String s1 = new String("hello");
        String s2 = new String("hello");
        System.out.println(s1 == s2);//false
        System.out.println(s1.equals(s2));//true

        String s3 = new String("hello");
        String s4 = "hello";
        System.out.println(s3 == s4);//false
        System.out.println(s3.equals(s4));//true

        String s5 = "hello";
        String s6 = "hello";
        System.out.println(s5 == s6);//true
        System.out.println(s5.equals(s6));//true
    }

    /**
     * 注意：
     *（1）字符串如果是变量相加，先开空间，再拼接。
     *（2）字符串如果是常量相加，是先加，然后在常量池找，如果有就直接返回，否则，就创建。
     */
    private static void test4() {
        String s1 = "hello";
        String s2 = "world";
        String s3 = "helloworld";

        //s1+s2是变量相加，先开空间，再拼接。所以地址不会相同
        System.out.println(s3 == s1 + s2);//false
        System.out.println(s3.equals((s1 + s2)));// true

        // "hello" + "world" 是字面值常量相加，所以先加,得到"helloworld",然后在常量池中找,结果找到了 "helloworld"
        System.out.println(s3 == "hello" + "world");//false 这个我们错了，应该是true
        System.out.println(s3.equals("hello" + "world"));// true

        // 对于 73、74行，通过反编译看源码，我们知道这里已经做好了处理。
        // System.out.println(s3 == "helloworld");
        // System.out.println(s3.equals("helloworld"));
    }
}
