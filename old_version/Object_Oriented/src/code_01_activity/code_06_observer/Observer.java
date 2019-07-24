package code_01_activity.code_06_observer;

/**
 * 观察者（Observer）的
 * 注册功能需要调用主题的 registerObserver() 方法。
 *
 * 主题（Subject）是被观察的对象，而其所有依赖者（Observer）称为观察者。
 */
public interface Observer {
    void update(float temp, float humidity, float pressure);
}
