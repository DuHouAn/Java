package code_01_activity.code_10_visitor;

/**
 * ConcreteVisitor：具体访问者，存储遍历过程中的累计结果
 */
public class GeneralReport implements Visitor{
    private int customersNo;
    private int ordersNo;
    private int itemsNo;

    @Override
    public void visit(Customer customer) {
        System.out.println(customer.getName());
        customersNo++;
    }

    @Override
    public void visit(Order order) {
        System.out.println(order.getName());
        ordersNo++;
    }

    @Override
    public void visit(Item item) {
        System.out.println(item.getName());
        itemsNo++;
    }

    public void displayResults() {
        System.out.println("Number of customers: " + customersNo);
        System.out.println("Number of orders:    " + ordersNo);
        System.out.println("Number of items:     " + itemsNo);
    }
}
