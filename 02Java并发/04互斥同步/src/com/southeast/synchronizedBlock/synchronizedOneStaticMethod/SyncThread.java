package com.southeast.synchronizedBlock.synchronizedOneStaticMethod;

public class SyncThread implements Runnable{
    private static int count=0;

    @Override
    public void run() {
        try {
            show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getCount(){
        return count;
    }

    private static synchronized void show() throws InterruptedException { //同步一个静态代码块
        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName()+"=="+(count++));
            Thread.sleep(100);
        }

    }
}
