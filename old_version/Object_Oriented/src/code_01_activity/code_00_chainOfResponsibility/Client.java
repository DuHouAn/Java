package code_01_activity.code_00_chainOfResponsibility;

/**
 * Created by 18351 on 2018/12/28.
 */
public class Client {
    public static void main(String[] args) {
        Handler h1=new ConcreteHandler1(null); //h1是没有后继的
        Handler h2=new ConcreteHandler2(h1);//h2的后继是h1

        Request r1=new Request(RequestType.TYPE1,"request-1");
        Request r2=new Request(RequestType.TYPE2,"request-2");


        //使多个对象都有机会处理请求，从而
        //TODO:避免请求的发送者和接受者之间的耦合关系。
        //将这些对象连成一条链，并沿着这条链发送请求，一直到有一个对象处理它为止。
        //h2-->h1，这里使用h2处理r1,最终交给h1处理
        h2.handleRequest(r1);
        //h2-->h1，这里h2直接处理
        h2.handleRequest(r2);
    }
}
