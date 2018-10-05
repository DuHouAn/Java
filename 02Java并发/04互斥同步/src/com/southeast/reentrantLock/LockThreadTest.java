package com.southeast.reentrantLock;

import com.southeast.synchronizedBlock.synchronizedOneMethod.SyncThread;

public class LockThreadTest {
    public static void main(String[] args) {
        LockThread lt=new LockThread();

        Thread t1=new Thread(lt);
        Thread t2=new Thread(lt);

        t2.start();
        t1.start();
    }
}
