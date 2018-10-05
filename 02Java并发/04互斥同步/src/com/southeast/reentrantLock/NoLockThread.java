package com.southeast.reentrantLock;

public class NoLockThread implements Runnable{
    private int count=0;

    @Override
    public void run() {
        try {
                for(int i=0;i<10;i++) {
                    System.out.println(Thread.currentThread().getName() + "==" + (count++));
                    Thread.sleep(100);
                }
        } catch (InterruptedException e) {
                e.printStackTrace();
        }
    }
}
