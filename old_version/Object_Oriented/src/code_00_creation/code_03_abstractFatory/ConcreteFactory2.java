package code_00_creation.code_03_abstractFatory;

public class ConcreteFactory2 extends AbstractFactory{
    @Override
    public AbstractProductA createProdctA() {
        return new ProductA2();
    }

    @Override
    public AbstractProductB createProdctB() {
        return new ProductB2();
    }
}
