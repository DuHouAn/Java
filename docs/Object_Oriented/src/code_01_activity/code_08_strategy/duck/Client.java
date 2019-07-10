package code_01_activity.code_08_strategy.duck;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Client {
    public static void main(String[] args) {
        Duck duck = new Duck();
        duck.setQuackBehavior(new Squeak());
        duck.perform();
        duck.setQuackBehavior(new Quack());
        duck.perform();
    }
}
