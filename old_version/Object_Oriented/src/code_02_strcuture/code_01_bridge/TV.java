package code_02_strcuture.code_01_bridge;

/**
 * TV 表示电视，指代 Implementor。
 */
public abstract class TV {
    public abstract void on();

    public abstract void off();

    public abstract void turnChannel();
}
