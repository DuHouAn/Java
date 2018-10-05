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
public class SerializableStudentDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //write();
        read();
    }

    public static void read() throws IOException, ClassNotFoundException {
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream("student.txt"));

        List<Student> list=new ArrayList<Student>();

        list.add((Student)ois.readObject());
        list.add((Student)ois.readObject());

        for(Student s:list){
            System.out.println(s.getName()+"\t"+s.getAge());
        }
        ois.close();
    }

    public static void write() throws IOException {
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("student.txt"));

        //定义对象
        Student s=new Student();
        s.setName("张三");
        s.setAge(123);
        oos.writeObject(s);

        Student s2=new Student();
        s2.setName("李四");
        s2.setAge(34);
        oos.writeObject(s2);

        oos.close();
    }
}
