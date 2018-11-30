package code_00_thread.threadSynchronization.reentrantLock;

// 同一时间段只能有1个线程执行run方法
public class LockThreadTest {
    public static void main(String[] args) {
        LockThread lt=new LockThread();

        Thread t1=new Thread(lt);
        Thread t2=new Thread(lt);

        t1.start();
        t2.start();
    }
}
