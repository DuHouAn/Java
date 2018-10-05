package com.southeast.serializable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ObjectInputStream
 *      Object readObject()  从ObjectInputStream读取一个对象。
 *
 * ObjectOutputStream
 *      void writeObject(Object obj)    将指定的对象写入ObjectOutputStream。
 */
public class SerializablePersonDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //write();
        read();
    }

    public static void read() throws IOException, ClassNotFoundException {
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream("person.txt"));

        List<Person> list=new ArrayList<Person>();

        list.add((Person)ois.readObject());
        list.add((Person)ois.readObject());

        for(Person p:list){
            System.out.println(p.getName()+"\t"+p.getAge());
        }
        ois.close();
    }

    public static void write() throws IOException {
        ObjectOutputStream ois=new ObjectOutputStream(new FileOutputStream("person.txt"));

        //定义对象
        Person p=new Person();
        p.setName("林青霞");
        p.setAge(27);
        ois.writeObject(p);

        Person p2=new Person();
        p2.setName("王祖贤");
        p2.setAge(23);
        ois.writeObject(p2);

        ois.close();
    }
}
