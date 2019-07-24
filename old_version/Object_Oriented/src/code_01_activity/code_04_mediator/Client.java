package code_01_activity.code_04_mediator;

/**
 * Created by 18351 on 2018/12/29.
 */
public class Client {
    public static void main(String[] args) {
        Alarm alarm = new Alarm();
        CoffeePot coffeePot = new CoffeePot();
        Calendar calender = new Calendar();
        Sprinkler sprinkler = new Sprinkler();

        Mediator mediator = new ConcreteMediator(alarm, coffeePot, calender, sprinkler);
        // 闹钟事件到达，调用中介者就可以操作相关对象
        alarm.onEvent(mediator);
    }
}
