package code_00_thread.threadSynchronization.juc_aqs;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 18351 on 2018/12/1.
 */
public class CyclicBarrierExample2 {
    private static int threadCount = 10;

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(5);

        ExecutorService executorService= Executors.newCachedThreadPool();


        for (int i = 0; i < threadCount; i++) {
            int finalI = i;
            executorService.execute(()->{
                try {
                    System.out.println("before..."+ finalI);
                    cyclicBarrier.await();
                    System.out.println("after..."+ finalI);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
