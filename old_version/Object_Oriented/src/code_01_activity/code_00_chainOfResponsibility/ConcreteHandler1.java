package code_01_activity.code_00_chainOfResponsibility;

/**
 * Created by 18351 on 2018/12/28.
 */
public class ConcreteHandler1 extends Handler{
    public ConcreteHandler1(Handler successor) {
        super(successor);
    }

    @Override
    protected void handleRequest(Request request) {
        if(request.getType()==RequestType.TYPE1){
            System.out.println(request.getName() + " is handle by ConcreteHandler1");
            return;
        }
        //如果有后继处理器，继续进行处理
        if(successor!=null){
            successor.handleRequest(request);
        }
    }
}
