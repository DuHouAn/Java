package code_02_strcuture.code_01_bridge;


/**
 * Created by 18351 on 2019/1/2.
 */
public class ConcreteRemoteControl2 extends RemoteControl{
    public ConcreteRemoteControl2(TV tv){
        super(tv);
    }

    @Override
    public void on() {
        System.out.println("ConcreteRemoteControl2.on()");
        tv.on();
    }

    @Override
    public void off() {
        System.out.println("ConcreteRemoteControl2.off()");
        tv.off();
    }

    @Override
    public void turnChannel() {
        System.out.println("ConcreteRemoteControl2.tuneChannel()");
        tv.turnChannel();
    }
}
