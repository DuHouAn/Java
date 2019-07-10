package code_01_activity.code_00_chainOfResponsibility;

/**
 * Handler：
 * 定义处理请求的接口，并且实现后继链（successor）
 */
public abstract class Handler {
    protected Handler successor;

    public Handler(Handler successor){
        this.successor=successor;
    }

    //其中,参数Request是自定义类
    protected abstract void handleRequest(Request request);
}
