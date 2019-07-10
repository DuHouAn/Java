package code_02_strcuture.code_03_decorator.beverage;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Mocha extends CondimentDecorator{
    public Mocha(Beverage beverage){
        super(beverage);
    }

    @Override
    public double cost() {
        return 1+beverage.cost();
    }
}
