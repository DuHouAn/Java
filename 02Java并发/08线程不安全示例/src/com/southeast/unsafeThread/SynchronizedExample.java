package com.southeast.unsafeThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedExample {
    private int cnt=0;

    public synchronized void add(){
        cnt++;
    }

    public int get(){
        return cnt;
    }

    public static void main(String[] args) throws InterruptedException {
        final int threadSize=1000;
        final SynchronizedExample example=new SynchronizedExample();

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

        StringBuffer sb=new StringBuffer();
        sb.append(1);
    }
}
