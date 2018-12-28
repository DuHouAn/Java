package code_00_creation.code_04_builder;

/**
 * 指导者负责指导装配过程，但是不负责每步具体的实现。
 */
public class Director {

    private AbstractComputerBuilder computerBuilder;

    public void setComputerBuilder(AbstractComputerBuilder computerBuilder) {
        this.computerBuilder = computerBuilder;
    }

    public Product getProduct() {
        return computerBuilder.getProduct();
    }

    public void constructComputer() {
        computerBuilder.buildProduct();
        computerBuilder.buildMaster();
        computerBuilder.buildScreen();
        computerBuilder.buildKeyboard();
        computerBuilder.buildMouse();
        computerBuilder.buildAudio();
    }
}
