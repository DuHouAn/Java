package com.southeast.reflectExamples;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 获取方法
 * 获取某个Class对象的方法集合，主要有以下几个方法：
 * （1）public Method[] getDeclaredMethods() throws SecurityException
 * getDeclaredMethods()方法返回类或接口声明的所有方法，包括公共、保护、默认（包）访问和私有方法，但不包括继承的方法。
 *
 * （2）public Method[] getMethods() throws SecurityException
 * getMethods()方法返回某个类的所有公用（public）方法，包括其继承类的公用方法。
 *
 * （3）public Method getMethod(String name, Class<?>... parameterTypes)
 * getMethod方法返回一个特定的方法，其中第一个参数为方法名称，后面的参数为方法的参数对应Class的对象
 */
public class Code_03_GetMethods {
    public static void main(String[] args) throws NoSuchMethodException {
        Class clazz=Obj.class;

        Method[] methods=clazz.getDeclaredMethods();
        for(Method method:methods){
            System.out.println(method.getName());
        }
        System.out.println("===================================");

        Method[] methods2=clazz.getMethods();//返回某个类的所有公用（public）方法，包括其继承类的公用方法。会有许多的Object的方法
        for(Method method:methods2){
            System.out.println(method.getName());
        }
        System.out.println("===================================");

        Method methods3=clazz.getMethod("sub",int.class,int.class);
        System.out.println(methods3.getName());
        System.out.println(methods3);
    }
}
