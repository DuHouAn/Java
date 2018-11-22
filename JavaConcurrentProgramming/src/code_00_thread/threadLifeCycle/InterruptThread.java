package code_00_thread.threadLifeCycle;

/**
 * Created by 18351 on 2018/11/22.
 */
public class InterruptThread {
    private static class A extends Thread {
        int i=1;
        @Override
        public void run() {
            while (true) {
                System.out.println(i);
                //的isInterrupted()方法来判断该线程的中断状态
                System.out.println(this.isInterrupted());
                try {
                    System.out.println("我马上去sleep了");
                    Thread.sleep(2000);
                    //当一个线程处于sleep、wait、join这三种状态之一的时候，如果此时他的中断状态为true，
                    //那么它就会抛出一个InterruptedException的异常，并将中断状态重新设置为false。

                    //终止线程
                    this.interrupt();
                } catch (InterruptedException e) {
                    System.out.println("异常捕获了"+this.isInterrupted());
                    return;
                }
                i++;
            }
        }
    }

    public static void main(String[] args) {
        A a=new A();
        a.start();
    }
}
/**
 * 输出结果
 1
 false
 我马上去sleep了
 2
 true
 我马上去sleep了
 异常捕获了false
 */
