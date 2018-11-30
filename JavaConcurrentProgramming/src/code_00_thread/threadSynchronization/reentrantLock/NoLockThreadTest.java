package code_00_thread.threadSynchronization.reentrantLock;

public class NoLockThreadTest {
    public static void main(String[] args) {
        NoLockThread lt=new NoLockThread();

        Thread t1=new Thread(lt);
        Thread t2=new Thread(lt);

        t2.start();
        t1.start();
    }
}
