package code_01_string;

/**
 * s1 和 s2 采用 new String() 的方式新建了两个不同字符串，
 * s3 和 s4 是通过 s1.intern() 方法取得一个字符串引用。
 * intern() 首先把 s1 引用的字符串放到 String Pool 中，然后返回这个字符串引用。
 * 因此 s3 和 s4 引用的是同一个字符串。
 */
public class StringDemo1 {
    public static void main(String[] args) {
        String s1=new String("abc");
        String s2=new String("abc");
        System.out.println(s1==s2);//false

        //可以使用 String 的intern() 方法在运行过程中将字符串添加到 String Pool 中
        String s3=s1.intern();
        String s4=s1.intern();
        System.out.println(s3==s4);//true

        //如果是采用 "bbb" 这种字面量的形式创建字符串，会自动地将字符串放入 String Pool 中。
        String s5="bbb";
        String s6="bbb";
        System.out.println(s5==s6);//true
    }
}
