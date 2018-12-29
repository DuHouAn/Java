package code_01_activity.code_04_mediator;

//Concrete Colleague
public class Sprinkler extends Colleague{
    @Override
    public void onEvent(Mediator mediator) {
        mediator.doEvent("colleague");
    }

    public void doSprinkler(){
        System.out.println("doSpringkler()");
    }
}
