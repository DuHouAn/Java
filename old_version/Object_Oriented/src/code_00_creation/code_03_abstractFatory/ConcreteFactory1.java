package code_00_creation.code_03_abstractFatory;

public class ConcreteFactory1 extends AbstractFactory{
    @Override
    public AbstractProductA createProdctA() {
        return new ProductA1();
    }

    @Override
    public AbstractProductB createProdctB() {
        return new ProductB1();
    }
}
