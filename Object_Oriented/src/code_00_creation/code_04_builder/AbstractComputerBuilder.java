package code_00_creation.code_04_builder;

/**
 * 生成器的抽象类
 * 负责具体实现每步的对象
 */
public abstract class AbstractComputerBuilder {
    protected Product product;

    public Product getProduct() {
        return product;
    }

    public void buildProduct(){
        product=new Product();
        System.out.println("生产出一台电脑");
    }

    public abstract void buildMaster();
    public abstract void buildScreen();
    public abstract void buildKeyboard();
    public abstract void buildMouse();
    public abstract void buildAudio();
}
