package code_02_strcuture.code_05_flyweight;

/**
 * intrinsicState：内部状态，享元对象共享内部状态
 * extrinsicState：外部状态，每个享元对象的外部状态不同
 */
public class ConcreteFlyWeight implements FlyWeight{
    private String intrinsicState;

    public ConcreteFlyWeight(String intrinsicState){
        this.intrinsicState=intrinsicState;
    }

    @Override
    public void doOperation(String extrinsicState) {
        System.out.println("Object address: " + System.identityHashCode(this));
        System.out.println("IntrinsicState: " + intrinsicState);
        System.out.println("ExtrinsicState: " + extrinsicState);
    }
}
