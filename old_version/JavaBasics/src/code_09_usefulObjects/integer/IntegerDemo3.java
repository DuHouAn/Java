package code_09_usefulObjects.integer;

/**
 * 常用的基本进制转换
 *      public static String toBinaryString(int i)
 *      public static String toOctalString(int i)
 *      public static String toHexString(int i)
 *
 * 十进制到其他进制
 *      public static String toString(int i,int radix)
 * 由这个我们也看到了进制的范围：2-36
 * 为什么呢?0,...9,a...z
 *
 * 其他进制到十进制
 *      public static int parseInt(String s,int radix)
 */
public class IntegerDemo3 {
    public static void main(String[] args) {
        //test();
        //test2();
        test3();
    }

    //常用的基本进制转换
    public static void test(){
        System.out.println(Integer.toBinaryString(100));//1100100
        System.out.println(Integer.toOctalString(100));//144
        System.out.println(Integer.toHexString(100));//64
        System.out.println("-----------------------------");
    }

    //十进制到其他进制
    public static void test2(){
        System.out.println(Integer.toString(100, 10));//100
        System.out.println(Integer.toString(100, 2));//1100100
        System.out.println(Integer.toString(100, 8));//144
        System.out.println(Integer.toString(100, 16));//64
        System.out.println(Integer.toString(100, 5));//400
        System.out.println(Integer.toString(100, 7));//202
        //进制的范围在2-36之间，超过这个范围，就作为十进制处理
        System.out.println(Integer.toString(100, -7)); //100
        System.out.println(Integer.toString(100, 70));//100
        System.out.println(Integer.toString(100, 1));//100
        System.out.println(Integer.toString(100, 37));//100

        System.out.println(Integer.toString(100, 17));//5f
        System.out.println(Integer.toString(100, 32));//34
        System.out.println(Integer.toString(100, 36));//2s
        System.out.println("-------------------------");
    }

    //任意进制转换为十进制
    public static void test3(){
        System.out.println(Integer.parseInt("100", 10));//100
        System.out.println(Integer.parseInt("100", 2));//4
        System.out.println(Integer.parseInt("100", 8));//64
        System.out.println(Integer.parseInt("100", 16));//256
        System.out.println(Integer.parseInt("100", 23));//529
        //NumberFormatException,因为二进制是不可能存在 123的
        //System.out.println(Integer.parseInt("123", 2));
    }
}
