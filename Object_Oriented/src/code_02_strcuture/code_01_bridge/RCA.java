package code_02_strcuture.code_01_bridge;

/**
 * Created by 18351 on 2019/1/2.
 */
public class RCA extends TV{
    @Override
    public void on() {
        System.out.println("RCA.on()");
    }

    @Override
    public void off() {
        System.out.println("RCA.off()");
    }

    @Override
    public void turnChannel() {
        System.out.println("RCA.turnChannel()");
    }
}
