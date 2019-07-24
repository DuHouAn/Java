package code_01_string;

/**
 * String s=new String("aaa")和String s="aaa"的区别？
 * 前者会创建一个或者多个对象
 * 后者只会创建一个或者零个对象
 */
public class StringDemo2 {
    public static void main(String[] args) {
        /**
         * "abc" 属于字符串字面量，因此编译时期会在 String Pool 中创建一个字符串对象，指向这个 "abc" 字符串字面量
         * 而使用 new 的方式会在堆中创建一个字符串对象。
         */
        String s = new String("abc");
    }
}
