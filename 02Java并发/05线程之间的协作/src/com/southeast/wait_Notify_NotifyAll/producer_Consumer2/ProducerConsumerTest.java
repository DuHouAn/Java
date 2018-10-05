package com.southeast.wait_Notify_NotifyAll.producer_Consumer2;

/**
 * 线程安全问题：
 * （1）是否是多线程环境
 * （2）是否有共享数据
 * （3）是否有多条语句操作共享数据
 */
//等待唤醒机制:保证一人一次
public class ProducerConsumerTest {
    public static void main(String[] args) {
        Baozi baozi=new Baozi();

        Producer producer=new Producer(baozi);
        Consumer consumer=new Consumer(baozi);

        Thread pro=new Thread(producer);
        Thread con=new Thread(consumer);

        pro.start();
        con.start();
    }
}
