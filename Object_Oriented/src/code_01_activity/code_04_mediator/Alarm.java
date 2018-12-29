package code_01_activity.code_04_mediator;

/**
 * concrete colleageue
 */
public class Alarm extends Colleague{
    @Override
    public void onEvent(Mediator mediator) {
        mediator.doEvent("alarm");
    }

    public void doAlarm(){
        System.out.println("doAlarm()");;
    }
}
