package code_01_activity.code_06_observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题（Subject）具有注册和移除观察者、并通知所有观察者的功能，
 * 主题是通过维护一张观察者列表来实现这些操作的。
 */
public class WeatherData implements Subject{
    //主题（Subject）具有注册和移除观察者、并通知所有观察者的功能，
    //主题是通过维护一张观察者列表来实现这些操作的。
    private List<Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData(){
        observers=new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        if(observers.size()>0){
            observers.remove(0);
        }
    }

    @Override
    public void notifyObserver() {
        //通知所有的观察者
        for(Observer observer:observers){
            observer.update(temperature,humidity,pressure);
        }
    }

    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObserver();
    }
}
