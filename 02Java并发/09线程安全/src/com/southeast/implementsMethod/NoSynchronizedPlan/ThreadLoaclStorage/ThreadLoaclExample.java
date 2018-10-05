package com.southeast.implementsMethod.NoSynchronizedPlan.ThreadLoaclStorage;

/**
 * 使用 java.lang.ThreadLocal 类来实现###线程本地存储###功能。
 *
 * 对于以下代码，
 * thread1 中设置 threadLocal 为 1，
 * thread2 设置 threadLocal 为 2。
 * 过了一段时间之后，thread1 读取 threadLocal 依然是 1，不受 thread2 的影响。
 */
public class ThreadLoaclExample {
    public static void main(String[] args) {
        final ThreadLocal threadLocal=new ThreadLocal();

        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set(1);
                try {
                    Thread.sleep(100);
                    System.out.println(threadLocal.get());
                    threadLocal.remove();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal.set(2);
                threadLocal.remove();
            }
        });

        t1.start();
        t2.start();
    }
}
