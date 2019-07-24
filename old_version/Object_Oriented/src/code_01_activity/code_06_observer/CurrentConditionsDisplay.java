package code_01_activity.code_06_observer;

/**
 * Created by 18351 on 2018/12/31.
 */
public class CurrentConditionsDisplay implements Observer{
    public CurrentConditionsDisplay(Subject weatherData){
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        System.out.println("CurrentConditionsDisplay.update: " + temp + " " + humidity + " " + pressure);
    }
}
