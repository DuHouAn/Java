package com.southeast.interruptedMethod;

public class InterruptMethodExample {
    private static class MyThread extends Thread{
        @Override
        public void run() {
            int i=0;
            while(!interrupted()){ //interrupted()返回ture 表示线程处于中断状态
                //这里的循环判断条件 就是 非中断状态
                System.out.println(getName()+"=="+i);
                i++;
            }
            System.out.println(getName()+" end");
        }
    }

    public static void main(String[] args) {
        MyThread my=new MyThread();
        my.start();

        try {
            Thread.sleep(300);
            my.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
