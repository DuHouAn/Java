package com.southeast.objectStream;

import java.io.Serializable;

/**
 * TODO:NotSerializableException:未序列化异常
 *
 * 类通过实现 java.io.Serializable 接口以启用其序列化功能。
 * 未实现此接口的类将无法使其任何状态序列化或反序列化。
 * 该接口居然没有任何方法，类似于这种没有方法的接口被称为标记接口。
 *
 * java.io.InvalidClassException:
 * stream classdesc serialVersionUID = -2071565876962058344,
 * local class serialVersionUID = -8345153069362641443
 * 为什么会有问题呢?
 * 		Person类实现了序列化接口，那么它本身也应该有一个标记值。
 * 		这个标记值假设是100。
 * 		开始的时候：
 * 		Person.class -- id=100
 * 		wirte数据： oos.txt -- id=100
 * 		read数据: oos.txt -- id=100
 *
 * 		现在：
 * 		Person.class -- id=200
 * 		wirte数据： oos.txt -- id=100
 * 		read数据: oos.txt -- id=100
 * 我们在实际开发中，可能还需要使用以前写过的数据，不能重新写入。怎么办呢?
 * 回想一下原因是因为它们的id值不匹配。
 * 每次修改java文件的内容的时候,class文件的id值都会发生改变。
 * 而读取文件的时候，会和class文件中的id值进行匹配。所以，就会出问题。
 * 但是呢，如果我有办法，让这个id值在java文件中是一个固定的值，这样，你修改文件的时候，这个id值还会发生改变吗?
 * 不会。现在的关键是我如何能够知道这个id值如何表示的呢?
 * 不用担心，你不用记住，也没关系，点击鼠标即可。
 *
 * 我们要知道的是：
 * 		看到类实现了序列化接口的时候,可以自动产生一个序列化id值。
 * 		而且产生这个值以后，我们对类进行任何改动，它读取以前的数据是没有问题的。
 *
 * 注意：
 * 		我一个类中可能有很多的成员变量，有些我不想进行序列化。请问该怎么办呢?
 * 		使用transient关键字声明不需要序列化的成员变量
 */
public class Person implements Serializable {
    private static final long serialVersionUID = -2071565876962058344L;

    private String name;

    //private transient int age;//transient关键字声明不需要序列化的成员变量
    private int age;


    public Person() {
        super();
    }

    public Person(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

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

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + "]";
    }
}
