package com.southeast.reflectExamples;

/**
 * 判断是否是某个类实例
 * public native boolean isInstance(Object obj);
 */
public class Code_01_IsInstanceOfClass {
    public static void main(String[] args) {
        Class clazz=Obj.class;

        Obj obj=new Obj();

        //判断是否是某个类的实例
        System.out.println(clazz.isInstance(obj));
    }
}
