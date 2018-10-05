package com.southeast.staticKeyWord;

/**
 * 非静态内部类依赖于外部类的实例，而静态内部类不需要。
 */
public class OuterClass {
    class InnerClass{ //非静态内部类依赖于外部类的实例

    }

    static class StaticInnerClass{ //静态内部类不需要依赖外部类实例

    }

    public static void main(String[] args) {
        OuterClass outerClass=new OuterClass();
        InnerClass innerClass=outerClass.new InnerClass();
        StaticInnerClass staticInnerClass=new StaticInnerClass();//不依赖于外部类，可以直接创建对象
    }
}

/*
初始化顺序
（1）静态变量和静态语句块优先于实例变量和普通语句块，
静态变量和静态语句块的初始化顺序取决于它们在代码中的顺序。

public static String staticField = "静态变量";
static {
    System.out.println("静态语句块");
}

public String field = "实例变量";
{
    System.out.println("普通语句块");
}

（2）构造函数的初始化
public InitialOrderTest() {
    System.out.println("构造函数");
}

存在继承的情况下，初始化顺序为：
父类（静态变量、静态语句块）
子类（静态变量、静态语句块）
===============================
父类（实例变量、普通语句块）
父类（构造函数）
子类（实例变量、普通语句块）
子类（构造函数）
*/