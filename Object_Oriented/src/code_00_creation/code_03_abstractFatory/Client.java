package code_00_creation.code_03_abstractFatory;

/**
 * Created by 18351 on 2018/12/27.
 */
public class Client {
    public static void main(String[] args) {
        AbstractFactory factory=new ConcreteFactory1();
        /**
         * 创建对象的家族这一概念是在 Client 体现，
         * Client 要通过 AbstractFactory 同时调用两个方法来创建出两个对象，
         * 在这里这两个对象就有很大的相关性，Client 需要同时创建出这两个对象。
         */
        AbstractProductA productA=factory.createProdctA();
        AbstractProductB productB=factory.createProdctB();
    }
}
