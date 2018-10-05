package com.southeast.await_Signal_SignalAll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Lock 来获取一个Condition对象。
public class AwaitSignalExampleTest {
    public static void main(String[] args) {
        ExecutorService service= Executors.newCachedThreadPool();

        final AwaitSignalExample awaitSignalExample=new AwaitSignalExample();

        service.execute(new Runnable() {
            @Override
            public void run() {
                awaitSignalExample.after();
            }
        });

        service.execute(new Runnable() {
            @Override
            public void run() {
                awaitSignalExample.before();
            }
        });
    }
}
