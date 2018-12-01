package code_00_thread.threadSynchronization.juc_aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by 18351 on 2018/12/1.
 */
public class SemaphoreExample2 {
    private static int clientCount = 3;
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
                        //获取一个许可
                        semaphore.acquire();
                        test(threadNum);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                        //释放一个许可
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
