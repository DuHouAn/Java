package code_00_creation.code_00_singleton;

/**
 * 饿汉式--线程安全
 * 线程不安全问题主要是由于 uniqueIntance被实例化了多次，
 * 如果uniqueInstance采用直接实例化的话，就不会被实例化多次，也就不会产生线程不安全的问题。
 * 但是直接实例化的方式也丢失了延迟实例化带来节约资源的优势。
 */
public class Singleton2 {
    //线程不安全问题主要是由于 uniqueIntance被实例化了多次，
    //如果uniqueInstance采用直接实例化的话，就不会被实例化多次，也就不会产生线程不安全的问题。
    private static Singleton2 uniqueInstance=new Singleton2();

    private Singleton2(){

    }

    public  static Singleton2 getUniqueInstance(){
        return uniqueInstance;
    }
}
