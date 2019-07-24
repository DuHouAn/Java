package code_02_strcuture.code_05_flyweight;

/**
 * intrinsicState：内部状态，享元对象共享内部状态
 * extrinsicState：外部状态，每个享元对象的外部状态不同
 */
public class Client {
    public static void main(String[] args) {
        FlyWeightFactory factory=new FlyWeightFactory();
        FlyWeight flyWeight=factory.getFlyWeight("aaa");
        FlyWeight flyWeight2=factory.getFlyWeight("aaa");
        flyWeight.doOperation("1");
        flyWeight2.doOperation("2");
    }
}
