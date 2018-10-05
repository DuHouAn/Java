package com.southeast.reflectExamples;

/**
 * 获得Class对象
 * 方法有三种
 * (1)使用Class类的forName静态方法
 *      public static Class<?> forName(String className)
 *
 * (2)直接获取某一个对象的class，比如:
 *      Class<?> klass = int.class;
 *      Class<?> classInt = Integer.TYPE;
 *
 * (3)调用某个对象的getClass()方法,比如:
 *      StringBuilder str = new StringBuilder("123");
 *      Class<?> klass = str.getClass();
 */
public class Code_00_GetClazzObject {
    public static void main(String[] args) throws ClassNotFoundException {
        Class clazz=Class.forName("com.southeast.reflectExamples.Obj");
        System.out.println(clazz);

        Class clazz2=Obj.class;
        System.out.println(clazz2);

        Obj obj=new Obj();
        Class clazz3=obj.getClass();
        System.out.println(clazz3);

        System.out.println(clazz==clazz2);
        System.out.println(clazz2==clazz3);
    }
}
