package code_00_creation.code_01_simpleFactory;

/**
 * SimpleFactory 是简单工厂实现，它被所有需要进行实例化的客户类调用。
 */
public class SimpleFactory {
    public Product createProduct(int type) {
        if (type == 1) {
            return new ConcreteProductA();
        } else if (type == 2) {
            return new ConcreteProductB();
        }
        return new ConcreteProductC();
    }
}
