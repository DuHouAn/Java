package code_01_activity.code_01_command.remoteControl;

/**
 * 遥控器,也就是命令的调用者
 */
public class RemoteContol {
    private Command[] onCommands;
    private Command[] offCommands;
    private final int slotNum = 7;

    public RemoteContol(){
        onCommands=new LightOnCommand[slotNum];
        offCommands=new LightOffCommand[slotNum];
    }

    public void setOnCommand(Command command, int slot) {
        onCommands[slot] = command;
    }

    public void setOffCommand(Command command, int slot) {
        offCommands[slot] = command;
    }

    public void onButtonWasPushed(int slot) {
        onCommands[slot].execute();
    }

    public void offButtonWasPushed(int slot) {
        offCommands[slot].execute();
    }
}