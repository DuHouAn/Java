package code_01_activity.code_10_visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * ObjectStructure：对象结构，可以是组合结构，或者是一个集合。
 * 这里是一个组合结构
 */
public class CustomerGroup {
    private List<Customer> customers = new ArrayList<>();

    void accept(Visitor visitor) {
        for (Customer customer : customers) {
            customer.accept(visitor);
        }
    }

    void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
