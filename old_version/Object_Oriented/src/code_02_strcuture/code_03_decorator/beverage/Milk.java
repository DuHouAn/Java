package code_02_strcuture.code_03_decorator.beverage;

/**
 * 具体的装饰类，实现具体要向被装饰对象添加的功能。
 */
public class Milk extends CondimentDecorator{
    public Milk(Beverage beverage){
        super(beverage);
    }

    @Override
    public double cost() {
        return 1+beverage.cost();
    }
}
