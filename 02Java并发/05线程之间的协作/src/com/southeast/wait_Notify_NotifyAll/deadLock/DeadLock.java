package com.southeast.wait_Notify_NotifyAll.deadLock;

/**
 *死锁：
 *两个或两个以上的线程在争夺资源的过程中，发生的一种相互等待的现象。
 */
public class DeadLock extends Thread {
    private boolean flag;

    public DeadLock(boolean flag){
        this.flag=flag;
    }

    @Override
    public void run() {
        if(flag){
            synchronized (MyLock.objA){
                System.out.println("if objA");//2、线程d进入 使用了 objA,但是没有使用objA-->假设d2抢到了执行权
                //4、d要执行 ，但是 objB还没有被释放，无法进入，只好等待
                synchronized (MyLock.objB){
                    System.out.println("if objB");
                }
            }
        }else{
            synchronized (MyLock.objB){
                System.out.println("else objB"); //1、线程d2 进入 使用了 objB,但是没有释放objB -->此时线程d执行
                //3、d2要执行 ，但是 objA还没有被释放，无法进入，只好等待 -->假设d抢到了执行权
                synchronized (MyLock.objA){
                    System.out.println("else objA");
                }
            }
        }
    }
}
