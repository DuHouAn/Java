package com.southeast.serializable;

import java.io.Serializable;

/**
 *  注意：
 * 		我一个类中可能有很多的成员变量，有些我不想进行序列化。请问该怎么办呢?
 * 		使用transient关键字声明不需要序列化的成员变量
 */
public class Student implements Serializable{
    private static final long serialVersionUID = -2071565876962058344L;//常量，这样class文件中的值就不会发生变化

    private String name;
    private transient int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
