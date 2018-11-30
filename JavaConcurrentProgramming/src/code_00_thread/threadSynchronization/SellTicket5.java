package code_00_thread.threadSynchronization;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 18351 on 2018/11/30.
 */
public class SellTicket5 implements Runnable{
    private static int tickets = 100; //这一百张票是线程的共有资源

    //创建锁对象
    private ReentrantLock locker=new ReentrantLock();

    @Override
    public void run() { //同步方法的锁是this
        // 是为了模拟一直有票
        while (true) {
            sell();
        }
    }

    private void sell(){
        try{
            locker.lock();
            if (tickets > 0) {
                // 为了模拟更真实的场景，我们稍作休息
                Thread.sleep(100); //t1进来了并休息，t2进来了并休息，t3进来了并休息，
                System.out.println(Thread.currentThread().getName() + "正在出售第" + (tickets--) + "张票");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            locker.unlock();
        }
    }
}
