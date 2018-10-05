package com.southeast.BlockingQueue;

import com.southeast.Producer_Consumer.Baozi;

import java.util.concurrent.BlockingQueue;

//消费者
public class Consumer implements Runnable{
    private BlockingQueue<Baozi2> queue;

    public Consumer(BlockingQueue<Baozi2> queue)
    {
        this.queue=queue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Baozi2 baozi= queue.take();
                baozi.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
