package code_00_creation.code_02_factoryMethod;

/**
 * 具体的工厂生产具体的东西
 *
 * ConcreteProductAFactory就生产ConcreteProductA()
 */
public class ConcreteProductAFactory extends Factory{
    @Override
    public Product fatoryMethod() {
        return new ConcreteProductA();
    }
}
