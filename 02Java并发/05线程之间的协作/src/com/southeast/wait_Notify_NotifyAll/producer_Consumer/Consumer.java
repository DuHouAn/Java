package com.southeast.wait_Notify_NotifyAll.producer_Consumer;

//消费者
public class Consumer implements Runnable{
    private Baozi baozi;

    public Consumer(Baozi baozi) {
        this.baozi = baozi;
    }

    @Override
    public void run() {
        while(true){
            synchronized (baozi){
               if(!baozi.isFlag()){ //false表示没有数据
                   try {
                       baozi.wait();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }

               //消费数据
               consume();

               baozi.setFlag(false);
               baozi.notify();
            }
        }
    }

    public void consume(){
        System.out.println("吃"+baozi.getName()+"包子，馅是"+baozi.getPie());
    }
}
