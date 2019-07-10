package code_02_strcuture.code_03_decorator.beverage;

/**
 * ConcreteComponent：具体的组件对象，实现组件对象接口，
 * 通常就是被装饰器装饰的原始对象，也就是可以给这个对象添加职责。
 */
public class DarkRoast implements Beverage{
    @Override
    public double cost() {
        return 3;
    }
}
