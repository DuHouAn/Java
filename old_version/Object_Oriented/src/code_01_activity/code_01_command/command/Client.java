package code_01_activity.code_01_command.command;

/**
Client不是通常意义上的测试客户端，
 主要功能是要创建命令对象并设定它的接收者。
 */
public class Client {
    public static void main(String[] args) {
        //接收者,其实是命令的真正执行者
        Receiver receiver=new Receiver();
        Command c=new ConcreteCommand(receiver);
        //命令的调用者
        Invoker invoker=new Invoker(c);
        invoker.executeCommand();
    }
}
