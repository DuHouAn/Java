package code_02_strcuture.code_01_bridge;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Sony extends TV{
    @Override
    public void on() {
        System.out.println("Sony.on()");
    }

    @Override
    public void off() {
        System.out.println("Sony.off()");
    }

    @Override
    public void turnChannel() {
        System.out.println("Sony.turnChannel()");
    }
}
