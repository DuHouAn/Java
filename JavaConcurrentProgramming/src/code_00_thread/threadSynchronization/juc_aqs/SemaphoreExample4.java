package code_00_thread.threadSynchronization.juc_aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by 18351 on 2018/12/1.
 */
public class SemaphoreExample4 {
    private static int clientCount = 2;
    private static int totalRequestCount = 10;

    public static void main(String[] args) {
        ExecutorService executorService= Executors.newCachedThreadPool();

        final Semaphore semaphore=new Semaphore(clientCount);

        for(int i=0;i<totalRequestCount;i++){
            final int threadNum=i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        if(semaphore.tryAcquire()){
                            //尝试获取一个许可
                            test(threadNum);
                            semaphore.release();
                        }
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        System.out.println("run:"+threadNum);
        Thread.sleep(1000);
    }
}