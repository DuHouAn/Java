package code_01_activity.code_10_visitor;

/**
 * Visitor：访问者，为每一个 ConcreteElement 声明一个 visit 操作
 */
public interface Visitor {
    void visit(Customer customer);

    void visit(Order order);

    void visit(Item item);
}
