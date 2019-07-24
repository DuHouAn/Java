package code_00_creation.code_00_singleton;

/**
 * 懒汉式--线程安全
 * 对getUniqueInstance()加锁，
 * 则在一个时间点只能有一个线程能够进入该方法，从而避免了对uniqueInstance进行多次实例化的问题。
 *
 * 但是，当一个线程进入该方法后，其他线程试图进入该方法就必须等待，因此性能上会有损耗。
 */
public class Singleton3 {
    //线程不安全问题主要是由于 uniqueIntance被实例化了多次，
    //如果uniqueInstance采用直接实例化的话，就不会被实例化多次，也就不会产生线程不安全的问题。
    private static Singleton3 uniqueInstance=new Singleton3();

    private Singleton3(){

    }

    //当一个线程进入该方法之后，其它试图进入该方法的线程都必须等待
    public  synchronized static Singleton3 getUniqueInstance(){
        return uniqueInstance;
    }
}
