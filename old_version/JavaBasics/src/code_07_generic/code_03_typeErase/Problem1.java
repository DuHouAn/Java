package code_07_generic.code_03_typeErase;

import java.util.ArrayList;

/**
 * 类型擦除
 * 问题一：在Java中不允许创建泛型数组。
 */
public class Problem1 {
    public static void main(String[] args) {
       // List<Integer> [] listsOfArray = new List<Integer>[2];;  // compile-time error
        /*
        解析：
        compile-time error,我们站在编译器的角度来考虑这个问题:
        先来看一下下面这个例子：
        Object[] strings = new String[2];
        strings[0] = "hi";   // OK
        strings[1] = 100;    // An ArrayStoreException is thrown.
        字符串数组不能存放整型元素，而且这样的错误往往要等到代码==运行的时候===才能发现，编译器是无法识别的。

        接下来我们再来看一下假设Java支持泛型数组的创建会出现什么后果：
        Object[] stringLists = new List<String>[];  // compiler error, but pretend it's allowed
        stringLists[0] = new ArrayList<String>();   // OK
        // An ArrayStoreException should be thrown, but the runtime can't detect it.
        stringLists[1] = new ArrayList<Integer>();

        假设我们支持泛型数组的创建，由于运行时期类型信息已经被擦除，
        JVM实际上根本就不知道new ArrayList<String>()和new ArrayList<Integer>()的区别。
        类似这样的错误假如出现才实际的应用场景中，将非常难以察觉。
        * */

        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        //因为 存在类型擦除，实际上就是c1和c2使用的是同一个.class文件
        System.out.println(c1 == c2); // true
    }
}
