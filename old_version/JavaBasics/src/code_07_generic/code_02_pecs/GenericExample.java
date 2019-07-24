package code_07_generic.code_02_pecs;

import java.util.ArrayList;
import java.util.List;

/**
 * <? extends T>表示T及T的子类
 */
public class GenericExample {
    public static void main(String[] args) {
        //List<? extends Fruit> fruits = new ArrayList<Apple>();
        List<? extends Fruit> fruits = new ArrayList<Fruit>();
        //? extends Fruit表示的是Fruit及其子类
        // Compile Error: can't add any type of object:
        //fruits.add(new Apple());
        //fruits.add(new Orange());
        //fruits.add(new Fruit());
        //fruits.add(new Object());
        //fruits.add(null); // Legal but uninteresting
    }
}
