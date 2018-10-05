package com.southeast.interruptedException;

public class InterruptExample {
    private static class MyThread extends Thread{
        @Override
        public void run() {
            try {
                //通过调用一个线程的 interrupt() 来中断该线程，
                //如果该线程处于阻塞、限期等待或者无限期等待状态，
                //那么就会抛出InterrutedException，从而提前结束该线程。
               Thread.sleep(1000);
                System.out.println("Thread run");
            } catch (InterruptedException e) {
                System.out.println("线程中断了");
            }
            //System.out.println("Thread run");
        }
    }

    public static void main(String[] args){
        MyThread my=new MyThread();
        my.start();//启动线程

        try {
            //主线程等待 3 s ,超过3 s 就中断 my 线程
            Thread.sleep(3000);
            my.interrupt();//中断线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //通过调用一个线程的 interrupt() 来中断该线程，
        //如果该线程处于阻塞、限期等待或者无限期等待状态，
        //那么就会抛出InterrutedException，从而提前结束该线程。

        System.out.println("Main run");
    }
}
