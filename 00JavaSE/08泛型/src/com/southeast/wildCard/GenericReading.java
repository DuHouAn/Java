package com.southeast.wildCard;

import java.util.Arrays;
import java.util.List;

//这里可以看出 Apple和Orange都是Fruit子类

/**
 * Apple和Fruit之间肯定是存在联系，然而编译器却无法识别，
 * 那怎么在泛型代码中解决这个问题呢？我们可以通过使用通配符来解决这个问题
 *
 *  PECS(Producer Extends Consumer Super)原则:
 *  对于实现了<? extends T>的集合类只能将它视为Producer向外提供(get)元素,
 * ，而不能作为Consumer来对外获取(add)元素。
 */
public class GenericReading {
    static List<Apple> apples = Arrays.asList(new Apple());
    static List<Fruit> fruit = Arrays.asList(new Fruit());

    static class Reader<T>{ //Reader<T>是自定义的泛型类
       /* T readExact(List<T> list){
            return list.get(0);
        }*/
       T readExact(List<? extends T> list){//使用通配符来解决这个问题
           return list.get(0); //TODO :get()方法
       }
    }

    static void fun1(){
        Reader<Fruit> fruitReader=new Reader<Fruit>();
        //Fruit f=fruitReader.readExact(apples);// Errors: List<Fruit> cannot be applied to List<Apple>.
        Fruit f=fruitReader.readExact(apples);//正确
    }

    public static void main(String[] args) {
        fun1();
    }
}
