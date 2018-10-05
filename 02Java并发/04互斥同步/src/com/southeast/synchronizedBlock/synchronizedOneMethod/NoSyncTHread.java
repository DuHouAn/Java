package com.southeast.synchronizedBlock.synchronizedOneMethod;

public class NoSyncTHread implements Runnable{
    private static int count=0;

    @Override
    public void run() {
        //这里未 使用 synchronized代码块
            for(int i=0;i<10;i++){
                try {
                    System.out.println(Thread.currentThread().getName()+"=="+(count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }

    public int getCount(){
        return count;
    }
}
