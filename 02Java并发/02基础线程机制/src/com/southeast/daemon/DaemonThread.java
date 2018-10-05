package com.southeast.daemon;

public class DaemonThread extends Thread{
    @Override
    public void run() {
       for(int i=0;i<100;i++){
           System.out.println(getName()+"\t"+"和大哥打天下"+i);
       }
    }
}
