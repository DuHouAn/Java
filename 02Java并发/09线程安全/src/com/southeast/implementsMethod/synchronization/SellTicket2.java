package com.southeast.implementsMethod.synchronization;

import java.util.concurrent.locks.ReentrantLock;

public class SellTicket2 implements Runnable{
    private int tickets=100;
    private ReentrantLock lock=new ReentrantLock();

    @Override
    public void run() {
        while(true){
            sell();
        }
    }

    private void sell(){
        try{
            lock.lock();
            if (tickets > 0) {
                // 为了模拟更真实的场景，我们稍作休息
                Thread.sleep(100); //t1进来了并休息，t2进来了并休息，t3进来了并休息，
                System.out.println(Thread.currentThread().getName() + "正在出售第" + (tickets--) + "张票");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
