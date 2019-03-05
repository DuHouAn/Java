package code_01_activity.code_01_command.command;

/**
 * 将命令封装成对象中，具有以下作用：
 * (1)使用命令来参数化其它对象
 * (2)将命令放入队列中进行排队
 * (3)将命令的操作记录到日志中
 * (4)支持可撤销的操作
 */
public interface Command {
    //执行命令的对应操作
    public abstract void execute();
}
