package code_00_creation.code_01_simpleFactory;

/**
 * Created by 18351 on 2018/12/27.
 */
public class Client {
    public static void main(String[] args) {
        SimpleFactory simpleFactory = new SimpleFactory();
        Product product = simpleFactory.createProduct(1);
        // do something with the product
    }
}
