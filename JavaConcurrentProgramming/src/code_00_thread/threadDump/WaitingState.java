package code_00_thread.threadDump;

/**
 * Created by 18351 on 2018/11/27.
 */
public class WaitingState {
    private static Object object = new Object();

    public static void main(String[] args)
    {
        Runnable task = new Runnable() {

            @Override
            public void run()
            {
                synchronized (object)
                {
                    long begin = System.currentTimeMillis();
                    long end = System.currentTimeMillis();

                    // 让线程运行5分钟,会一直持有object的监视器
                    while ((end - begin) <= 5 * 60 * 1000)
                    {
                        try
                        {
                            // 进入等待的同时,会进入释放监视器
                            object.wait();
                        } catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        new Thread(task, "t1").start();
        new Thread(task, "t2").start();
    }
}
