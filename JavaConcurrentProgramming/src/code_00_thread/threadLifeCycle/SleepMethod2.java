package code_00_thread.threadLifeCycle;

/**
 * sleep是静态方法**，最好不要用Thread的实例对象调用它，
 * 因为它睡眠的始终是当前正在运行的线程，而不是调用它的线程对象，
 * 它只对正在运行状态的线程对象有效。
 */
public class SleepMethod2 {
    public static void main(String[] args) throws InterruptedException {
        MyRunnable myRunnable=new MyRunnable();
        Thread t1=new Thread(myRunnable);
        Thread t2=new Thread(myRunnable);
        t1.start();
        t2.start();
    }
}
