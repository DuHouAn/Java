package com.southeast.unsafeThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {
    private int cnt=0;
    private ReentrantLock lock=new ReentrantLock();

    public void add(){
        try{
            lock.lock();
            cnt++;
        }finally {
            lock.unlock();
        }

    }

    public int get(){
        return cnt;
    }

    public static void main(String[] args) throws InterruptedException {
        final int threadSize=1000;
        final LockExample example=new LockExample();

        final CountDownLatch countDownLatch=new CountDownLatch(threadSize);
        ExecutorService executorService= Executors.newCachedThreadPool();
        for(int i=0;i<threadSize;i++){
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    example.add();
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println(example.get());
    }
}
