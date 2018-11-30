package code_00_thread.threadSynchronization.reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//ReentrantLock 是 java.util.concurrent包中的锁。

public class LockThread implements Runnable{
    //ReentrantLock 是 java.util.concurrent包中的锁。
    private Lock lock=new ReentrantLock();
    private int count=0;

    @Override
    public void run() {
        lock.lock();//上锁
        try {
                anotherLock();
                for(int i=0;i<10;i++) {
                    System.out.println(Thread.currentThread().getName() + "==" + (count++));
                    Thread.sleep(100);
                }
        } catch (InterruptedException e) {
                e.printStackTrace();
        }finally {
            lock.unlock();//确保释放锁，从而避免发生死锁
        }
    }

    //加锁的方法
    public void anotherLock() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " invoke anotherLock");
        } finally {
            lock.unlock();
        }
    }
}
