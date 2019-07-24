package code_01_activity.code_01_command.remoteControl;

/**
 * Created by 18351 on 2018/12/28.
 */
public class LightOnCommand implements Command{
    private Light light;

    public LightOnCommand(Light light){
        this.light=light;
    }

    @Override
    public void execute() {
        light.on();
    }
}
