package code_01_activity.code_10_visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * ConcreteElement
 */
public class Customer implements Element{
    private String name;

    //顾客和订单是一对多的关系
    private List<Order> orders = new ArrayList<>();

    Customer(String name) {
        this.name = name;
    }

    void addOrder(Order order) {
        orders.add(order);
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
        for (Order order : orders) {
            order.accept(visitor);
        }
    }
}
