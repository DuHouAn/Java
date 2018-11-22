package code_00_thread.threadLifeCycle;

import java.util.Date;

/**
 * Created by 18351 on 2018/11/22.
 */
public class InterruptThread2 {
    private static class B extends Thread {
        @Override
        public void run() {
            System.out.println("开始执行："+new Date());
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                System.out.println("线程被终止了");
            }
            System.out.println("执行结束:"+new Date());
        }
    }

    public static void main(String[] args) {
        B b=new B();
        b.start();
        try {
            //超过3s后b线程还没有醒，就终止该线程
            Thread.sleep(3000);
            b.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
/**
 * 输出结果:
 开始执行：Thu Nov 22 19:41:33 CST 2018
 线程被终止了
 执行结束:Thu Nov 22 19:41:36 CST 2018
 */
