package com.southeast.staticKeyWord;

/**
 * 静态变量：（类变量），也就是说这个变量属于类的，类所有的实例都共享静态变量，
 * 可以直接通过类名来访问它。静态变量在内存中只存在一份。
 * 实例变量：每创建一个实例就会产生一个实例变量，它与该实例同生共死。
 */
public class A {
    private int x;
    private static int y;

    static{ //静态语句块在类初始化时运行一次。
        System.out.println("123");
    }

    //静态方法在类加载的时候就存在了，它不依赖于任何实例。
    //所以金泰方法必须有实现，也就是说它不能是抽象方法。
    public static void fun1(){
        //int a=this.y; error只能访问所属类的静态字段和静态方法，方法中不能有 this 和 super 关键字。
        //(因为类加载的时候，静态方法就存在了，其不依赖于任何实例)
    }

    public static void main(String[] args) {
        A a=new A();
        int x=a.x;
        int y=A.y;

        A a2=new A();
    }
}
