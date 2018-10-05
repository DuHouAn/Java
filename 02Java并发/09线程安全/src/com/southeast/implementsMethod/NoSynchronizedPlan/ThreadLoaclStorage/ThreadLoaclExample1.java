package com.southeast.implementsMethod.NoSynchronizedPlan.ThreadLoaclStorage;

/**
 * 每个 Thread 都有一个 ThreadLocal.ThreadLocalMap 对象，
 * Thread 类中就定义了 ThreadLocal.ThreadLocalMap 成员。
 */
public class ThreadLoaclExample1 {
    public static void main(String[] args) {
        final ThreadLocal threadLocal1=new ThreadLocal();
        final ThreadLocal threadLocal2=new ThreadLocal();

        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal1.set(1);
                threadLocal2.set(1);
            }
        });

        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal1.set(2);
                threadLocal2.set(2);
            }
        });

        thread1.start();
        thread2.start();
    }
}
