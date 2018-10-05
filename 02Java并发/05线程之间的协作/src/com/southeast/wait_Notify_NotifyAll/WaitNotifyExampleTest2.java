package com.southeast.wait_Notify_NotifyAll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaitNotifyExampleTest2 {
    public static void main(String[] args) {
        ExecutorService service= Executors.newFixedThreadPool(2);

        final WaitNotifyEaxmple example=new WaitNotifyEaxmple();

        service.execute(new Runnable() {
            @Override
            public void run() {
                example.after();
            }
        });

        service.execute(new Runnable() {
            @Override
            public void run() {
                example.before();
            }
        });
    }
}
