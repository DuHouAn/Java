package code_01_activity.code_10_visitor;

public class Client {
    public static void main(String[] args) {
        //用户1三个订单
        Customer customer1 = new Customer("customer1");
        customer1.addOrder(new Order("order1", "item1"));
        customer1.addOrder(new Order("order2", "item1"));
        customer1.addOrder(new Order("order3", "item1"));

        //用户2 一个订单
        Order order = new Order("order_a");
        order.addItem(new Item("item_a1"));
        order.addItem(new Item("item_a2"));
        order.addItem(new Item("item_a3"));
        Customer customer2 = new Customer("customer2");
        customer2.addOrder(order);

        //ObjectStructure：对象结构，可以是组合结构，或者是一个集合。
        CustomerGroup objectStructure=new CustomerGroup();
        objectStructure.addCustomer(customer1);
        objectStructure.addCustomer(customer2);

        //Visitor,访问者，存储遍历过程中的累计结果
        GeneralReport visitor=new GeneralReport();
        objectStructure.accept(visitor);
        visitor.displayResults();
    }
}