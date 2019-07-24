package code_01_activity.code_08_strategy.price;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Price {
    private Strategy strategy;

    public double price(double goodsPrice){
        return strategy.calcPrice(goodsPrice);
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
