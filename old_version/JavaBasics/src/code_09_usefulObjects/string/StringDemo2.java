package code_09_usefulObjects.string;

/**
 * Created by 18351 on 2018/12/26.
 */
public class StringDemo2 {
    public static void main(String[] args) {
        test2();
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
     * 看程序，写结果
     *
     * * String s = new String(“hello”)和String s = “hello”;的区别?
     * 有。前者会创建2个对象，后者创建1个对象。
     *
     * ==和qeuals的区别
     * TODO：==:比较引用类型，比较的是地址值是否相同
     * TODO：equals:比较引用类型，默认也是比较地址值是否相同，
     * String类重写了equals()方法，比较的是内容是否相同。
     */
    public static void test2(){
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
}
