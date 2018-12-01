package code_00_thread.threadSynchronization.juc_aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by 18351 on 2018/12/1.
 */
public class CountdownLatchExample3 {
    //线程数量
    private static int threadCount=10;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService= Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch=new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum=i;
            executorService.execute(()->{
                try {
                    test(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        //上面线程如果在10 内未完成，则有可能会执行主线程
        System.out.println("Finished!");
        //不会立马关闭线程池，而是等待当前线程全部执行完再关闭线程池。
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        Thread.sleep(10);
        System.out.println("run: "+threadNum);
    }
}
