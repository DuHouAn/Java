package code_00_creation.code_00_singleton;

/**
 * 静态内部类实现
 * 当Singleton类加载时，静态内部类SingletonHolder没有被加载进内存。
 * 只有当调用 getUniqueInstance()方法从而触发SingletonHolder.INSTANCE时，
 * SingletonHolder才会被加载，此时初始化INSTANCE实例，
 * 由于是静态的域，因此只会被虚拟机在装载类的时候初始化一次。
 *
 * 这种方式不仅具有延迟初始化的好处，而且有虚拟机提供了对线程安全的支持。
 * 。
 */
public class Singleton5 {
    private Singleton5(){

    }

    private static class SingletonHolder{
        private static final Singleton5 INSTANCE=new Singleton5();
    }

    //getUniqueInstance()方法并没有被同步，并且只是执行一个域的访问，因此延迟初始化并没有增加任何访问成本。
    public static Singleton5 getUniqueInstance(){
        return SingletonHolder.INSTANCE;
    }
}
