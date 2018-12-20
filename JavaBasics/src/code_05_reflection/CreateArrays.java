package code_05_reflection;

import java.lang.reflect.Array;

/**
 * 利用反射创建数组
 *
 * Array类为java.lang.reflect.Array类。我们通过Array.newInstance()创建数组对象

 public static Object newInstance(Class<?> componentType, int length)
    throws NegativeArraySizeException {
        return newArray(componentType, length); //newArray()方法是一个Native方法
    }
 */
public class CreateArrays {
    public static void main(String[] args) {
        Class clazz=String.class;

        Object[] arr= (Object[]) Array.newInstance(clazz,25);
        Array.set(arr,0,"aaa");
        Array.set(arr,1,"bbb");
        Array.set(arr,2,"ccc");
        Array.set(arr,3,"ddd");
        Array.set(arr,4,"eee");
        Array.set(arr,5,"fff");
        System.out.println(arr.length); //25
        System.out.println(Array.get(arr,2));//ccc

    }
}
