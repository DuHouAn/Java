package code_01_activity.code_04_mediator;

//Concrete Colleague
public class Calendar extends Colleague{
    @Override
    public void onEvent(Mediator mediator) {
        mediator.doEvent("calendar");
    }

    public void doCalendar(){
        System.out.println("doCalendar()");
    }
}
