package code_01_activity.code_01_command.remoteControl;

/**
 * Light就是Receiver,就是命令的实际执行者
 */
public class Light {
    public void on() {
        System.out.println("Light is on!");
    }

    public void off() {
        System.out.println("Light is off!");
    }
}
