package com.southeast.synchronizedBlock.synchronizedOneClass;

public class SyncThreadTest {
    public static void main(String[] args) {
        SyncThread st=new SyncThread();

        Thread t1=new Thread(st);
        Thread t2=new Thread(st);

        t2.start();
        t1.start();
    }
}
