package code_06;

/**
 * 所有依赖静态类型来定位方法执行版本的分派动作，都称为静态分派。
 * 静态分派发生在编译阶段。
 * 静态分派最典型的应用就是方法重载。
 */
public class StaticDispatch {
    static abstract class Human {

    }

    static class Man extends Human {

    }

    static class Woman extends Human {

    }

    //sayhello是重载的方法
    public void sayhello(Human guy) {
        System.out.println("Human guy");

    }

    public void sayhello(Woman guy) {
        System.out.println("Woman guy");
    }

    public void sayhello(Man guy) {
        System.out.println("Man guy");

    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch staticDispatch = new StaticDispatch();
        staticDispatch.sayhello(man);// Human guy
        staticDispatch.sayhello(woman);// Human guy
    }
}
