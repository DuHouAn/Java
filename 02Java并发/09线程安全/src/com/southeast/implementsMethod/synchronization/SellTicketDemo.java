package com.southeast.implementsMethod.synchronization;

public class SellTicketDemo {
    public static void main(String[] args) {
        //SellTicket st=new SellTicket();
        SellTicket2 st=new SellTicket2();

        Thread t1=new Thread(st,"窗口1");
        Thread t2=new Thread(st,"窗口2");
        Thread t3=new Thread(st,"窗口3");

        t1.start();
        t2.start();
        t3.start();
    }
}
