package code_05_reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 获取构造器信息
 * 获取类构造器的用法与上述获取方法的用法类似。
 * 主要是通过Class类的getConstructor方法得到Constructor类的一个实例，
 * 而Constructor类有一个newInstance方法可以创建一个对象实例。
 *
 * public T newInstance(Object ... initargs)
 */
public class GetConstructorInfo {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz=Obj.class;
        Constructor constructor=clazz.getConstructor();
        //Constructor类有一个newInstance方法可以创建一个对象实例
        Obj obj=(Obj) constructor.newInstance();
        System.out.println(obj.sub(1,2));
    }
}
