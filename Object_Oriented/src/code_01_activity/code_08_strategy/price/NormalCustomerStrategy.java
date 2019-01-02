package code_01_activity.code_08_strategy.price;

/**
 * Created by 18351 on 2019/1/2.
 */
public class NormalCustomerStrategy implements Strategy{
    @Override
    public double calcPrice(double goodsPrice) {
        System.out.println("对于新客户或者是普通客户，没有折扣");
        return goodsPrice;
    }
}
