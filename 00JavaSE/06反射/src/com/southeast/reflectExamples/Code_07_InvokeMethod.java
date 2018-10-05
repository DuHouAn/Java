package com.southeast.reflectExamples;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 调用方法
 * 当我们从类中获取了一个方法后，我们就可以用invoke()方法来调用这个方法。
 * public Object invoke(Object obj, Object... args)
    throws IllegalAccessException, IllegalArgumentException,
    InvocationTargetException
 *      obj就是表示这个实例对象
 *      args表示该方法的参数
 */
public class Code_07_InvokeMethod {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class clazz=Class.forName("com.southeast.reflectExamples.Obj");

        Object obj=clazz.newInstance(); //  obj就是表示这个实例对象

        Method method=clazz.getMethod("sub",int.class,int.class);

        Integer num=(Integer)method.invoke(obj,1,2); //利用反射调用该党法
        System.out.println(num);
    }
}
