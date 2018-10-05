package com.southeast.synchronizedBlock.synchronizedOneClass;

public class SyncThread implements Runnable{
    private static int count=0;

    @Override
    public void run() {
        synchronized(Thread.class){
            //Thread.class 是字节码文件对象，说明两个线程之间可以调用Thread类的不同对象，也会进行同步
            for(int i=0;i<10;i++){
                try {
                    System.out.println(Thread.currentThread().getName()+"=="+(count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
       }
    }

    public int getCount(){
        return count;
    }
}
