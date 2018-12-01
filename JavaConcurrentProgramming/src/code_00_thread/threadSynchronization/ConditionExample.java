package code_00_thread.threadSynchronization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用 Lock 来获取一个 Condition 对象。
 */
public class ConditionExample {
    private Lock lock = new ReentrantLock();

    //使用 Lock 来获取一个 Condition 对象。
    private Condition condition = lock.newCondition();

    public void before() {
        lock.lock();
        try {
            System.out.println("before");
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void after() {
        lock.lock();
        try {
            condition.await();
            //进行线程一直等待，一直到被唤醒。
            System.out.println("after");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        ConditionExample example = new ConditionExample();
        executorService.execute(() -> example.after());
        executorService.execute(() -> example.before());
    }
}
//输出结果：
//before
//after
