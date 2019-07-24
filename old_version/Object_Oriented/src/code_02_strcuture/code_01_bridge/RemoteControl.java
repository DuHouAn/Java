package code_02_strcuture.code_01_bridge;

/**
 * Created by 18351 on 2019/1/2.
 */
public abstract class RemoteControl {
    protected TV tv;

    public RemoteControl(TV tv) {
        this.tv = tv;
    }

    public abstract void on();

    public abstract void off();

    public abstract void turnChannel();
}
