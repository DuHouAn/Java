package com.southeast.wait_Notify_NotifyAll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WaitNotifyExampleTest {
    public static void main(String[] args) {
        ExecutorService service= Executors.newCachedThreadPool();

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
