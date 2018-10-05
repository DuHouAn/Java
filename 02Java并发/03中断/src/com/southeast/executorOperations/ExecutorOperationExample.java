package com.southeast.executorOperations;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorOperationExample {
    public static void main(String[] args) {
        ExecutorService executorService= Executors.newCachedThreadPool();

        //可以使用 Lambda代替
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("Thread run");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            Thread.sleep(1000);//主线程等待1 s ,否则就中断该线程
            executorService.shutdownNow(); //如果调用的shutdownNow()方法则相当于调用每个线程interrupt()方法。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main run");
    }
}
