package code_00_thread.threadSynchronization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 只能用在同步方法或者同步控制块中使用，
 * 否则会在运行时抛出 IllegalMonitorStateExeception。
 */
public class WaitNotifyExample {
    private synchronized void before(){
        System.out.println("before");
        notify();
    }

    public synchronized void after() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after");
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        WaitNotifyExample example = new WaitNotifyExample();
        executorService.execute(() -> example.after());
        executorService.execute(() -> example.before());
    }
}
