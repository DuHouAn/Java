package com.southeast.CountdownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//CountdownLatch 用来控制一个线程等待多个线程。
//维护了一个计数器 cnt，每次调用 countDown() 方法会让计数器的值减 1，减到 0 的时候，
//那些因为调用 await() 方法而在等待的线程就会被唤醒。

//说明：
// CountDownLatch能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。
//特点：一线程在开始运行前等待n个线程执行完毕。
//典型应用场景：就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行。

public class CountdownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        final int totalThread=10;

        final CountDownLatch countDownLatch = new CountDownLatch(totalThread);

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < totalThread; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.print("run..");
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        //当计数器的值变为0时，在CountDownLatch上 await() 的线程就会被唤醒。
        //这里就是主线成 等待前 面的 10个线程运行完后，再运行。
        System.out.println("end");
        executorService.shutdown();
    }
}
