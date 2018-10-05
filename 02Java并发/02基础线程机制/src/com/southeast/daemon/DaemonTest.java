package com.southeast.daemon;

/**
 * 刘关张桃园结义后，
 * 关张以刘备马首是瞻，只要刘备死了，关张二人就自杀。
 */
public class DaemonTest {
    public static void main(String[] args) throws InterruptedException {
       DaemonThread t1=new DaemonThread();
       DaemonThread t2=new DaemonThread();

       t1.setName("张飞");
       t2.setName("关羽");

       //设置线程为守护线程，非守护线程结束，则杀死全部守护线程
       t1.setDaemon(true);
       t2.setDaemon(true);
       // 当正在运行的线程都是守护线程时，Java 虚拟机退出。
        //该方法必须在启动线程前调用。

        t1.start();
        t2.start();

        Thread.currentThread().sleep(10);
        Thread.currentThread().setName("刘备");
        for (int x = 0; x < 5; x++) {
            System.out.println(Thread.currentThread().getName() + ":" + "跟着我打天下"+x);
        }
    }
}

