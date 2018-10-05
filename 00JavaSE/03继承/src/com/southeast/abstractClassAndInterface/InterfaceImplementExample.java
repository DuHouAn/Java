package com.southeast.abstractClassAndInterface;

public class InterfaceImplementExample implements InterfaceExample{
    @Override
    public void fun1() {
        System.out.println("func1");
    }

    public static void main(String[] args) {
        InterfaceExample example=new InterfaceImplementExample();
        example.fun1();
        System.out.println(InterfaceExample.x);
    }
}
