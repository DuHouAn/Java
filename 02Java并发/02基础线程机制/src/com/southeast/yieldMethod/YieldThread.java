package com.southeast.yieldMethod;

public class YieldThread extends Thread{
    @Override
    public void run() {
        for(int i=0;i<100;i++){
            System.out.println(this.getName()+"\t"+i);
            Thread.yield(); //暂停当前正在执行的线程对象，并执行其他线程。
        }
    }
}
