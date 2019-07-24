package code_07_generic.code_02_pecs;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 对于实现了<? extends T>的集合类只能将它视为Producer向外提供(get)元素,
 * 而不能作为Consumer来对外获取(add)元素。
 */
public class GenericReading {
    private List<Apple> apples = Arrays.asList(new Apple());
    private List<Fruit> fruit = Arrays.asList(new Fruit());

    private class Reader<T>{ //Reader<T>是自定义的泛型类
         /*T readExact(List<T> list){
             return list.get(0);
         }*/
        T readExact(List<? extends T> list){//使用通配符来解决这个问题
            return list.get(0); //TODO :get()方法
        }
    }

    @Test
    public void test(){
        Reader<Fruit> fruitReader=new Reader<Fruit>();
        //Fruit f=fruitReader.readExact(apples);//使用readExact(List<T> list)  Errors: List<Fruit> cannot be applied to List<Apple>.
        Fruit f=fruitReader.readExact(apples);//正确
        System.out.println(f);
    }
}

