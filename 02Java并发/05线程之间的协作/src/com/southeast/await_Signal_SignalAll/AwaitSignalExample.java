package com.southeast.await_Signal_SignalAll;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Lock 来获取一个Condition对象。
public class AwaitSignalExample {
    private Lock lock=new ReentrantLock();

    private Condition condition=lock.newCondition();

    public void before(){
        lock.lock();
        try {
            System.out.println("before");
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void after(){
        lock.lock();
        try {
            condition.await();
            System.out.println("after");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}
