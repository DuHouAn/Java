package code_01_activity.code_10_visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * ConcreteElement
 */
public class Order implements Element {
    private String name;

    //订单和商品也是一对多的关系
    private List<Item> items = new ArrayList();

    Order(String name) {
        this.name = name;
    }

    Order(String name, String itemName) {
        this.name = name;
        this.addItem(new Item(itemName));
    }

    String getName() {
        return name;
    }

    void addItem(Item item) {
        items.add(item);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);

        for (Item item : items) {
            item.accept(visitor);
        }
    }
}
