package com.southeast.Producer_Consumer;

public class Baozi {
    private String name;
    private String pie;

    private boolean flag;//默认是 false,表示没有数据

    public synchronized void produce(String name,String pie){
        if(flag){ //表示有数据
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //生产数据
        this.name=name;
        this.pie=pie;
        System.out.println("做包子");

        this.flag=true;
        this.notify();
    }

    public synchronized void consume(){
        if(!flag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //消费数据
        System.out.println("吃"+this.name+"包子，馅是"+this.pie);

        this.flag=false;
        this.notify();
    }
}