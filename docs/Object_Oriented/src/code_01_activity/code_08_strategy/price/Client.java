package code_01_activity.code_08_strategy.price;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Client {
    public static void main(String[] args) {
        //1：选择并创建需要使用的策略对象
        Strategy strategy = new LargeCustomerStrategy ();
        //2：创建上下文
        Price ctx = new Price();
        ctx.setStrategy(strategy);

        //3：计算报价
        double price = ctx.price(1000);
        System.out.println("向客户报价："+price);
    }
}
