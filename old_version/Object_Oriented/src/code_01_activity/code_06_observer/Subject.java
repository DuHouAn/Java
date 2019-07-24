package code_01_activity.code_06_observer;

/**
 * 主题（Subject）具有注册和移除观察者、并通知所有观察者的功能，
 * 主题是通过维护一张观察者列表来实现这些操作的。
 *
 * 主题（Subject）是被观察的对象，而其所有依赖者（Observer）称为观察者。
 */
public interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObserver();
}
