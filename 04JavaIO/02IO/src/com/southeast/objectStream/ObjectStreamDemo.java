package com.southeast.objectStream;

import java.io.*;

/**
 * TODO:
 * 序列化流：
 *      把对象按照流一样的方式存入文本文件或者在网络中传输。
 *          对象 -- 流数据(ObjectOutputStream)
 * 反序列化流:
 *      把文本文件中的流对象数据或者网络中的流对象数据还原成对象。
 *          流数据 -- 对象(ObjectInputStream)
 */
public class ObjectStreamDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //write();
        read();
    }

    //反序列化
    public static void read() throws IOException, ClassNotFoundException {
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream("objectStream\\oos.txt"));

        Person obj=(Person)ois.readObject();
        ois.close();
        System.out.println(obj);
    }

    //序列化
    public static void write() throws IOException {
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("objectStream\\oos.txt"));

        Person p=new Person("张三",27);

        oos.writeObject(p);// public final void writeObject(Object obj)

        oos.close();
    }
}
