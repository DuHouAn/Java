package code_00_thread.threadDump;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 18351 on 2018/11/27.
 */
public class TimedWaitingState {

    // java的显示锁,类似java对象内置的监视器
    private static Lock lock = new ReentrantLock();

    // 锁关联的条件队列(类似于object.wait)
    private static Condition condition = lock.newCondition();

    public static void main(String[] args)
    {
        Runnable task = new Runnable() {

            @Override
            public void run()
            {
                // 加锁,进入临界区
                lock.lock();

                try
                {
                    condition.await(5, TimeUnit.MINUTES);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                // 解锁,退出临界区
                lock.unlock();
            }
        };

        new Thread(task, "t1").start();
        new Thread(task, "t2").start();
    }
}
