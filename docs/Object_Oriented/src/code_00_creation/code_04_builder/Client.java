package code_00_creation.code_04_builder;

/**
 * 指导者就是可以重用的构建过程，
 * 而生成器是可以被切换的具体实现
 */
public class Client {
    public static void main(String[] args) {
        AbstractComputerBuilder computerBuilder=new HPComputerBuilder();
        AbstractComputerBuilder computerBuilder2=new DELLComputerBuilder();
        Director director=new Director();

        director.setComputerBuilder(computerBuilder);
        director.constructComputer();
        //获取PC
        Product pc=director.getProduct();

        director.setComputerBuilder(computerBuilder2);
        director.constructComputer();
        Product pc2=director.getProduct();
    }
}
