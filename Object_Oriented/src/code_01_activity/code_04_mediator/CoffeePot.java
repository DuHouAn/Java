package code_01_activity.code_04_mediator;

//Concrete Colleague
public class CoffeePot extends Colleague{
    @Override
    public void onEvent(Mediator mediator) {
        mediator.doEvent("coffeepot");
    }

    public void doCoffeePot(){
        System.out.println("doCoffeePot()");
    }
}
