package code_01_activity.code_01_command.command;

/**
 * 接收者,其实是命令的真正执行者
 */
public class Receiver {
    /**
     * 真正执行命令操作
     */
    public void action(){
        //真正执行命令操作的功能代码
        System.out.println("真正执行命令操作的功能代码");
    }
}
