package com.southeast.FutureTask;

import java.util.concurrent.*;

/**
 * Future可用于异步获取执行结果或取消执行任务的场景。
 * 当一个计算任务需要执行很长时间，那么就可以用FutureTask来封装这个任务，
 * 主线程在完成自己的任务之后再去获取结果。
 */
public class FutureTaskExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask=new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result=0;
                for(int i=0;i<100;i++){
                    Thread.sleep(10);
                    result+=i;
                }
                return result;
            }
        });

        Thread t=new Thread(futureTask);
        t.start();

        System.out.println("other task is running...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(futureTask.get());//主线程在完成自己的任务之后再去获取结果。
    }
}
