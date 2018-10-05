package com.southeast.Semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore就是操作系统中的信号量，可以控制对互斥资源的访问线程数。
 */
public class SemaphoreExample {
    public static void main(String[] args) {
        final int clientCount = 3;//3个客户端同时访问
        final int totalRequestCount = 10; //10次请求

        final Semaphore semaphore = new Semaphore(clientCount);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < totalRequestCount; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.print(semaphore.availablePermits() + " ");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }
                }
            });
        }
        executorService.shutdown();
    }
}
