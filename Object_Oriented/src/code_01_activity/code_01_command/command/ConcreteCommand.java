package code_01_activity.code_01_command.command;

/**
 * Created by 18351 on 2018/12/28.
 */
public class ConcreteCommand implements Command{
    private Receiver receiver;

    public ConcreteCommand(Receiver receiver){
        this.receiver=receiver;
    }

    @Override
    public void execute() {
        receiver.action();
    }
}
