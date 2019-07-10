package code_05_reflection;

import com.sun.corba.se.spi.ior.ObjectKey;

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
public class InvokeMethod {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class clazz=Class.forName("code_05_reflection.Obj");

        //先获取该类的实例对象
        Obj obj=(Obj)clazz.newInstance();

        Method method=clazz.getMethod("sub",int.class,int.class);

        Integer num=(Integer)method.invoke(obj,1,2);
        System.out.println(num);
    }
}
