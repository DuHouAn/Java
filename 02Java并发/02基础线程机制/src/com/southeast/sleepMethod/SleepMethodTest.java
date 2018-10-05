package com.southeast.sleepMethod;

public class SleepMethodTest {
    public static void main(String[] args) {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().sleep(10000);
                    for(int i=0;i<10;i++){
                        System.out.println(Thread.currentThread().getName()+"\t"+i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
}
