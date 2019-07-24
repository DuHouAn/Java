package code_02_strcuture.code_01_bridge;


/**
 * Created by 18351 on 2019/1/2.
 */
public class ConcreteRemoteControl1 extends RemoteControl{
    public ConcreteRemoteControl1(TV tv){
        super(tv);
    }

    @Override
    public void on() {
        System.out.println("ConcreteRemoteControl1.on()");
        tv.on();
    }

    @Override
    public void off() {
        System.out.println("ConcreteRemoteControl1.off()");
        tv.off();
    }

    @Override
    public void turnChannel() {
        System.out.println("ConcreteRemoteControl1.tuneChannel()");
        tv.turnChannel();
    }
}
