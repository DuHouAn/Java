package code_01_activity.code_08_strategy.price;

/**
 * Created by 18351 on 2019/1/2.
 */
public class OldCustomerStrategy implements Strategy{
    @Override
    public double calcPrice(double goodsPrice) {
        System.out.println("对于老客户，统一折扣5%");
        return goodsPrice*(1-0.05);
    }
}
