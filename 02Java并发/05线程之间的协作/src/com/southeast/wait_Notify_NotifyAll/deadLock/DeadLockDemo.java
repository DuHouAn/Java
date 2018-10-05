package com.southeast.wait_Notify_NotifyAll.deadLock;

public class DeadLockDemo {
    public static void main(String[] args) {
        DeadLock d=new DeadLock(true);
        DeadLock d2=new DeadLock(false);

        d.start();
        d2.start();
    }
}
