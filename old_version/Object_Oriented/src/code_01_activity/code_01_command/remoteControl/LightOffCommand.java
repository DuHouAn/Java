package code_01_activity.code_01_command.remoteControl;

/**
 * Created by 18351 on 2018/12/28.
 */
public class LightOffCommand implements Command{
    private Light light;

    public LightOffCommand(Light light){
        this.light=light;
    }

    @Override
    public void execute() {
        light.off();
    }
}
