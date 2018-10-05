package com.southeast.yieldMethod;

//yield()方法暂停当前正在执行的线程对象，并执行其他线程。
public class YieldThreadTest {
    public static void main(String[] args) {
        YieldThread yt1=new YieldThread();
        YieldThread yt2=new YieldThread();

        yt1.setName("林青霞");
        yt2.setName("刘意");

        yt1.start();
        yt2.start();
    }
}
