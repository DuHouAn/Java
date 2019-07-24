package code_02_strcuture.code_04_facade;

/**
 * Facade：定义子系统的多个模块对外的高层接口，
 * 通常需要调用内部多个模块，从而把客户的请求代理给适当的子系统对象。
 */
public class Facade {
    private SubSystem subSystem = new SubSystem();

    public void watchMovie() {
        subSystem.turnOnTV();
        subSystem.setCD("a movie");
        subSystem.starWatching();
    }
}
