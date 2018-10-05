package com.southeast.bufferPool;

/**
 * new Integer(123) 与 Integer.valueOf(123) 的区别在于，
 * new Integer(123) 每次都会新建一个对象，
 * 而 Integer.valueOf(123)可能会使用缓存对象，
 * 因此多次使用Integer.valueOf(123)会取得同一个对象的引用。
 */
public class BufferPoolTest {
    public static void main(String[] args) {
       Integer x=new Integer(123);
       Integer y=new Integer(123);
       System.out.println(x==y);

       Integer x2=Integer.valueOf(123);
       Integer y2=Integer.valueOf(123);

        /**
         * 编译器会在自动装箱过程中调用valueOf()方法，
         * 因此一次多个Integer实例使用自动装箱来创建并且值相同，
         * 那么就会引用相同的对象。
         */
        Integer m=123;
        Integer n=123;
        System.out.println(m==n);
    }
}

/*
自动装箱和自动拆箱
1、自动装
Integer x=4; //自动装箱 （基本类型转化为包装类型）
实际上就是 Integer x=Integer.valueOf(4);
2、自动拆箱
x=x+5
x=Integer.valueOf(x.intValue()+5);//先拆箱，再装箱。
 */
