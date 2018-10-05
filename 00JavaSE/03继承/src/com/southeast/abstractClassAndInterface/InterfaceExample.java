package com.southeast.abstractClassAndInterface;

public interface InterfaceExample {
    void fun1();

    /*default void fun2(){
    //从 Java 8 开始，接口也可以拥有默认的方法实现，这是因为不支持默认方法的接口的维护成本太高了。
        System.out.println("func2");
    }*/

    int x=123;
    //int y;//Variable 'y' might not have been initialized -->接口的成员变量默认都是 static 和 final 的。
    public int z=0;//  Modifier 'public' is redundant for interface fields
    // private int k = 0;   // Modifier 'private' not allowed here
    // protected int l = 0; // Modifier 'protected' not allowed here
    // private void fun3(); // Modifier 'private' not allowed here
}
