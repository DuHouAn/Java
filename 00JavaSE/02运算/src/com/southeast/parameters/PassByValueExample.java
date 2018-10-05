package com.southeast.parameters;

public class PassByValueExample {
    public static void main(String[] args) {
        Dog dog = new Dog("A");
        System.out.println(dog.getObjectAddress()); // Dog@4554617c
        func(dog); //这是里面的 dog的地址值发生变化，原来的dog地址值是不受影响的
        System.out.println(dog.getObjectAddress()); // Dog@4554617c
        System.out.println(dog.getName());          // A
    }

    private static void func(Dog dog) {
        //在将一个参数传入一个方法时，本质上是将对象的地址以值的方式传递到形参中。
        System.out.println(dog.getObjectAddress()); // Dog@4554617c
        dog = new Dog("B");
        //在方法中改变指针引用的对象，那么这两个指针此时指向的是完全不同的对象，一方改变其所指向对象的内容对另一方没有影响。
        System.out.println(dog.getObjectAddress()); // Dog@74a14482
        System.out.println(dog.getName());          // B
    }

}
