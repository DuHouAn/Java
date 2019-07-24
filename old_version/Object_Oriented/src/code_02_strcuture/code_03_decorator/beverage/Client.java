package code_02_strcuture.code_03_decorator.beverage;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Client {
    public static void main(String[] args) {
        Beverage beverage = new HouseBlend();
        System.out.println(beverage.cost()); //3.0

        beverage = new Mocha(beverage);
        System.out.println(beverage.cost()); //4.0

        beverage = new Milk(beverage);
        //本来3元的饮料，加上Mocha、Milk后就变成了5元
        System.out.println(beverage.cost()); //5.0
    }
}