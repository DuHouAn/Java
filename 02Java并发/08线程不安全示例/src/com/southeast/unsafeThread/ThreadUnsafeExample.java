package com.southeast.unsafeThread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  线程安全问题：
 *  （1）是否是多线程环境
 *  (2）是否有共享数据
 *  (3）是否有多条语句操作共享数据
 */
public class ThreadUnsafeExample {
    private int cnt=0;

    public void add(){
        cnt++;
    }

    public int get(){
        return cnt;
    }

    public static void main(String[] args) throws InterruptedException {
        final int threadSize=1000;
        final ThreadUnsafeExample example=new ThreadUnsafeExample();

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
