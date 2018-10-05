package com.southeast.implementCallable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable mc=new MyCallable();
        FutureTask<Integer> ft=new FutureTask<>(mc); //Callable返回类型是 FutureTask

        Thread thread=new Thread(ft);
        thread.start();
        System.out.println(ft.get());
    }
}
