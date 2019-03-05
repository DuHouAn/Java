package code_01_activity.code_06_observer;

/**
 * 天气预报
 */
public class WeatherForecast {
    public static void main(String[] args) {
        WeatherData weatherData=new WeatherData();
        Observer observer=new StatisticsDisplay(weatherData);
        Observer observer2=new CurrentConditionsDisplay(weatherData);

        weatherData.setMeasurements(0.0f,0.0f,0.0f);
        weatherData.setMeasurements(1.0f,1.0f,1.0f);
    }
}