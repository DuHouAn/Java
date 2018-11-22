package code_00_thread.threadLifeCycle;

/**
 * Created by 18351 on 2018/11/22.
 */
public class DaemonThreadEaxmple {
    private static class DaemonThread extends Thread{
        @Override
        public void run() {
            for(int i=0;i<100;i++){
                System.out.println(this.getName()+":"+i);
            }
        }
    }

    public static void main(String[] args) {
        Thread t1=new DaemonThread();
        Thread t2=new DaemonThread();
        t1.setName("张飞");
        t2.setName("关羽");

        //该方法必须在启动线程前调用。
        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();

        //主线程
        Thread.currentThread().setName("刘备");
        for(int i=0;i<5;i++){
            System.out.println(Thread.currentThread().getName()+":"+i);
        }
    }
}
