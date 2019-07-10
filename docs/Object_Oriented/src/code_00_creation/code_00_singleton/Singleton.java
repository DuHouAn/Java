package code_00_creation.code_00_singleton;

/**
 * 懒汉式--线程不安全
 * 私有静态变量 uniqueInstance被延迟实例化，
 * 好处：如果没有用到该类，就不会实例化uniqueInstance,从而节约资源。
 */
public class Singleton {
    private static Singleton uniquueInstance=null;

    private Singleton(){

    }

    public static Singleton getUniquueInstance(){
        /**
         * 多线程下不安全，
         * 如果有多个线程进入 if(uniqueInstance==null)并且uniqueInstance==null
         * 那么多个线程执行 uniqueInstance=new Singleton(),导致多次实例化uniqueInstance。
         */
        if(uniquueInstance==null){
            uniquueInstance=new Singleton();
        }
        return uniquueInstance;
    }
}
