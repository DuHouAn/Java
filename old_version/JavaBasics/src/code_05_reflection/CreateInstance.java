package code_05_reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 创建实例
 * 通过反射来生成对象主要有两种方式。
 * （1）使用Class对象的newInstance()方法来创建Class对象对应类的实例。
 * （2）先通过Class对象获取指定的Constructor对象，再调用Constructor对象的newInstance()方法来创建实例。
 * 这种方法可以用指定的构造器构造类的实例。
 */
public class CreateInstance {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //（1）使用Class对象的newInstance()方法来创建Class对象对应类的实例。
        Class clazz=String.class;
        String str=(String)clazz.newInstance();

        //（2）先通过Class对象获取指定的Constructor对象，再调用Constructor对象的newInstance()方法来创建实例。
        Constructor strConstructor=clazz.getConstructor(String.class);////获取String构造函数 String(String str)
        String str2=(String)strConstructor.newInstance("sss");
        System.out.println(str2);//sss
    }
}
