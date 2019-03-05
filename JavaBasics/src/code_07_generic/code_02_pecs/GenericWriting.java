package code_07_generic.code_02_pecs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 *
使用super的坏处是以后不能get容器里面的元素了，
 原因很简单，我们继续从编译器的角度考虑这个问题，
对于List<? super Apple> list，它可以有下面几种含义：
List<? super Apple> list = new ArrayList<Apple>();
List<? super Apple> list = new ArrayList<Fruit>();
List<? super Apple> list = new ArrayList<Object>();
当我们尝试通过list来get一个Apple的时候，可能会get得到一个Fruit，这个Fruit可以是Orange等其他类型的Fruit。
*/
public class GenericWriting {
    private List<Apple> apples = new ArrayList<Apple>();
    private List<Orange> oranges = new ArrayList<Orange>();
    private List<Fruit> fruit = new ArrayList<Fruit>();

    <T> void writeExact(List<T> list, T item) {
        list.add(item); //TODO :这里是add
    }

    <T> void writeWithWildcard(List<? super T> list, T item) {
        list.add(item);
    }

    void func1(){
        writeExact(apples,new Apple());
        writeExact(fruit,new Apple());
    }

    void func2(){
        writeWithWildcard(apples, new Apple());
        writeWithWildcard(fruit, new Apple());
    }

    @Test
    public void test(){
        func1();
        func2();
    }
}