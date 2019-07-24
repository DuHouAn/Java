package code_01_activity.code_01_command.remoteControl;

/**
 * Created by 18351 on 2018/12/28.
 */
public class Client {
    public static void main(String[] args) {
        Light light = new Light();
        Command lightOnCommand = new LightOnCommand(light);
        Command lightOffCommand = new LightOffCommand(light);
        RemoteContol remoteContol=new RemoteContol();
        remoteContol.setOnCommand(lightOnCommand, 0);
        remoteContol.setOffCommand(lightOffCommand, 0);

        //执行命令
        remoteContol.onButtonWasPushed(0);
        remoteContol.offButtonWasPushed(0);
    }
}
