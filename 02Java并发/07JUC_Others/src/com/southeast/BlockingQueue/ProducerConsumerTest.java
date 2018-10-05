package com.southeast.BlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 使用阻塞队列实现生产者和消费者
 * 参见 包 Producer_Consumer
 */
public class ProducerConsumerTest {
    private static BlockingQueue<Baozi2> queue=new ArrayBlockingQueue<Baozi2>(5);

    public static void main(String[] args) {
        Baozi2 baozi=new Baozi2();

        Producer producer=new Producer(baozi,queue);
        Consumer consumer=new Consumer(queue);

        Thread pro=new Thread(producer);
        Thread con=new Thread(consumer);

        pro.start();
        con.start();
    }
}
