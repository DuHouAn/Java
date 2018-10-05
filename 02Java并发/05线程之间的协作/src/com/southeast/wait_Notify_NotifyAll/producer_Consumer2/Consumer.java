package com.southeast.wait_Notify_NotifyAll.producer_Consumer2;

//消费者
public class Consumer implements Runnable{
    private Baozi baozi;

    public Consumer(Baozi baozi) {
        this.baozi = baozi;
    }

    @Override
    public void run() {
        while(true){
            baozi.consume();
        }
    }
}
