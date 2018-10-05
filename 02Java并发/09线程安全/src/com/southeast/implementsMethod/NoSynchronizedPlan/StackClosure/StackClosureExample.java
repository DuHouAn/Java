package com.southeast.implementsMethod.NoSynchronizedPlan.StackClosure;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StackClosureExample {
    public void add(){
        int cnt=0;
        for(int i=0;i<=100;i++){
            cnt+=i;
        }
        System.out.println(cnt);
    }

    public static void main(String[] args) {
        final StackClosureExample example=new StackClosureExample();
        ExecutorService service= Executors.newCachedThreadPool();

        service.execute(new Runnable() {
            @Override
            public void run() {
                example.add();
            }
        });
        service.execute(new Runnable() {
            @Override
            public void run() {
                example.add();
            }
        });
        service.shutdown();
    }
}
