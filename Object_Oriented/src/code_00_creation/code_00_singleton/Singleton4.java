package code_00_creation.code_00_singleton;

/**
 * 双重锁校验--线程安全
 * uniqueInstance只需要被实例化一次，之后就可以直接使用了。
 * 加锁操作只需要对实例化的部分代码进行,即当uniqueInstance没有被实例化时，才需要加锁。
 *
 * 双重校验先判断uniqueInstance是否已经被实例化，如果没有被实例化，那么才对实例化语句进行加锁。
 *
 */
public class Singleton4 {
    //采用volatile是有必要的。
    /**
     * 因为 unqieInstance=new Singleton();这段代码分三步执行。
     * 1.分配内存空间
     * 2.初始化对象
     * 3.将uniqueInstance指向分配的内存地址
     *
     * 但是由于JVM具有指令重排的特行，有可能执行顺序变为了1-->3-->2,
     * 这在单线程情况下自然是没有问题的。但如果是在多线程下，有可能获得的是因为
     * TODO:还没有被初始化的实例，导致程序出错。
     *
     * 使用volatile可禁止JVM指令重排，保证在多线程环境下也能正常运行。
     */
    private static volatile Singleton4 uniqueInstance=null;

    private Singleton4(){

    }

    public  static Singleton4 getUniqueInstance(){
        /**
         * 考虑下面的实现，也就是只使用了一个 if 语句。
         * 在 uniqueInstance == null 的情况下，如果两个线程都执行了 if 语句，那么两个线程都会进入 if 语句块内。
         * 虽然在 if 语句块内有加锁操作，但是两个线程都会执行 uniqueInstance = new Singleton();
         * 这条语句，只是先后的问题，那么就会进行两次实例化。
         * 因此必须使用双重校验锁，也就是需要使用两个 if 语句。
         */
        if(uniqueInstance==null){
            synchronized (Singleton4.class){
                if(uniqueInstance==null){
                    uniqueInstance=new Singleton4();
                }
            }
        }
        return uniqueInstance;
    }
}
