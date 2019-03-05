package code_00_creation.code_02_factoryMethod;

/**
 * 具体的工厂生产具体的东西
 */
public class ConcreteProductCFactory extends Factory{
    @Override
    public Product fatoryMethod() {
        return new ConcreteProductC();
    }
}
