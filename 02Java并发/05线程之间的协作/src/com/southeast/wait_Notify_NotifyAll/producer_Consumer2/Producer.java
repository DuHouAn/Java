package com.southeast.wait_Notify_NotifyAll.producer_Consumer2;

//生产者
public class Producer implements Runnable{
    private Baozi baozi;
    private int i=0;

    public Producer(Baozi baozi) {
        this.baozi = baozi;
    }

    @Override
    public void run() {
        while(true){
                baozi.produce("狗不理","肉");
        }
    }
}
