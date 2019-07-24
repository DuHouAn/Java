package code_01_activity.code_08_strategy.price;

/**
 * 策略，定义计算报价算法的接口
 */
public interface Strategy {
    /**
     * 计算报价
     * @param goodsPrice 商品原价
     * @return
     */
    double calcPrice(double goodsPrice);
}
