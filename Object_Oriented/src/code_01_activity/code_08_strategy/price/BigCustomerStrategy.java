package code_01_activity.code_08_strategy.price;

/**
 * Created by 18351 on 2019/1/2.
 */
public class BigCustomerStrategy implements Strategy{
    @Override
    public double calcPrice(double goodsPrice) {
        System.out.println("对于大客户，统一折扣10%");
        return goodsPrice*(1-0.1);
    }
}
