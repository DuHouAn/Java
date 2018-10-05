package com.southeast.joinThread;

public class ThreadJoinTest {
    public static void main(String[] args) {
        ThreadJoin tj=new ThreadJoin();

        Thread t1=new Thread(tj);
        Thread t2=new Thread(tj);
        Thread t3=new Thread(tj);

        t1.setName("李渊");
        t2.setName("李世民");
        t3.setName("李建成");

        t1.start();
        try {
            t1.join(); //public final void join():等待该线程终止。
            //t1调用该方法，表是就是其他线程要等待t1线程结束。
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t3.start();
        t2.start();
    }
}
