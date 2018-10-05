package com.southeast.implementRunnable;

public class RunnableTest {
    public static void main(String[] args) {
        MyRunnable myRunnable=new MyRunnable();
        Thread thread=new Thread(myRunnable);
        thread.start();
    }
}
