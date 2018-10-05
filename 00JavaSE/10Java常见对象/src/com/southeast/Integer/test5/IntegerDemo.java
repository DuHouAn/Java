package com.southeast.Integer.test5;

/**
 * * JDK5的新特性
 * 自动装箱：把基本类型转换为包装类类型
 * 自动拆箱：把包装类类型转换为基本类型
 *
 * 注意一个小问题：
 * 		在使用时，Integer  x = null;代码就会出现NullPointerException。
 * 		建议先判断是否为null，然后再使用。
 */
public class IntegerDemo {
    public static void main(String[] args) {
        //boxUnBox();
        test();

        Integer iii = null;
        // NullPointerException
        if (iii != null) {
            iii += 1000;
            System.out.println(iii);
        }
    }

    //自动装箱,拆箱
    public static void boxUnBox(){
        Integer integer=100;
        //实际上就是自动装箱 :  Integer integer=Integer.valueOf(100);
        integer+=200;
        //实际上是先自动拆箱，再自动装箱: integer.intValue()+200 --> Integer integer=Integer.valueOf(integer.intValue()+200);
        System.out.println((new StringBuilder("integer:")).append(integer).toString());
    }

    public static void test() {
        Integer integer=Integer.valueOf(100);
        integer=Integer.valueOf(integer.intValue()+200);
        System.out.println((new StringBuilder("integer:")).append(integer).toString());
    }
}
