package code_00_thread.threadLifeCycle;

/**
 * 与sleep类似，也是Thread类提供的一个**静态方法**，它也可以让当前正在执行的线程暂停，让出CPU资源给其他的线程。
 * 但是和sleep()方法不同的是，它不会进入到阻塞状态，而是进入到就绪状态。
 * yield()方法只是让当前线程暂停一下，重新进入就绪线程池中，让系统的线程调度器重新调度器重新调度一次，
 * 完全可能出现这样的情况：当某个线程调用yield()方法之后，线程调度器又将其调度出来重新进入到运行状态执行。
 *
 * 实际上，当某个线程调用了yield()方法暂停之后，
 * 优先级与当前线程相同，或者优先级比当前线程更高的就绪状态的线程更有可能获得执行的机会，
 * 当然，只是有可能，因为我们不可能精确的干涉cpu调度线程。
 */
public class YieldMethod {
    public static void main(String[] args) {
        Thread t1=new MyThread("低级", 1);
        Thread t2=new MyThread("中级", 5);
        Thread t3=new MyThread("高级", 10);
        t1.start();
        t2.start();
        t3.start();
    }
}
