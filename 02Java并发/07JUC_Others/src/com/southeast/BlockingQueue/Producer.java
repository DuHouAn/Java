package com.southeast.BlockingQueue;

import com.southeast.Producer_Consumer.Baozi;

import java.util.concurrent.BlockingQueue;

//生产者
public class Producer implements Runnable{
    private Baozi2 baozi;
    private BlockingQueue<Baozi2> queue;
    private int i=0;

    public Producer(Baozi2 baozi,BlockingQueue<Baozi2> queue)
    {
        this.baozi = baozi;
        this.queue=queue;
    }

    @Override
    public void run() {
        while(true){
            try {
                baozi.produce("狗不理","肉");
                queue.put(baozi);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
