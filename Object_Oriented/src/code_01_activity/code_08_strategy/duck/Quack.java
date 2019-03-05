package code_01_activity.code_08_strategy.duck;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Quack implements QuackBehavior{
    @Override
    public void quack() {
        System.out.println("quack");
    }
}
