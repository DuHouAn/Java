package com.southeast.implementsMethod.synchronization;

public class SellTicket implements Runnable{
    private int ticket=100;

    @Override
    public void run() {
        while(true){
          sell();
        }
    }

    private synchronized void sell() {
        if(ticket>0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+":正在出售第"+(ticket--)+"张票");
        }
    }
}
