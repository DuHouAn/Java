package com.southeast.wait_Notify_NotifyAll.producer_Consumer;

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
            synchronized (baozi){ //线程间通信，用的是同一把锁
                if(baozi.isFlag()){ //true，表示有数据，则等待，不生产
                    try {
                        baozi.wait(); //有数据就等待，并释放锁；从哪里等待，就会从哪里唤醒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //生产数据
                //produce();
                produce2();

                //修改标记
                baozi.setFlag(true);
                baozi.notify();//唤醒线程，唤醒并不表示可以立马执行，线程仍然要强CPU的执行权
            }
        }
    }

    public void produce(){
        if(i%2==0){
            baozi.setName("狗不理");
            baozi.setPie("肉");
        }else{
            baozi.setName("叉烧包");
            baozi.setPie("韭菜");
        }
        i++;
    }

    public void produce2(){
        baozi.setName("狗不理");
        baozi.setPie("肉");
        System.out.println("做包子");
    }
}
