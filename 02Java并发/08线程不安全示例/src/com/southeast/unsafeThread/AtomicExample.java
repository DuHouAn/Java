package com.southeast.unsafeThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {
    private AtomicInteger cnt=new AtomicInteger();
    //AtomicInteger保证多个线程修改的原子性

    public void add(){
        cnt.incrementAndGet();
    }

    public int get(){
        return cnt.get();
    }

    public static void main(String[] args) throws InterruptedException {
        final int threadSize=1000;
        final AtomicExample atomicExample=new AtomicExample();
        final CountDownLatch countDownLatch=new CountDownLatch(threadSize);
        ExecutorService service= Executors.newCachedThreadPool();

        for(int i=0;i<threadSize;i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    atomicExample.add();
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        service.shutdown();
        System.out.println(atomicExample.get());
    }
}
