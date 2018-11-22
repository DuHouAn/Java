package code_00_thread.threadLifeCycle;

/**
 * sleep是静态方法**，最好不要用Thread的实例对象调用它，
 * 因为它睡眠的始终是当前正在运行的线程，而不是调用它的线程对象，
 * 它只对正在运行状态的线程对象有效。
 */
public class SleepMethod {
    public static void main(String[] args) throws InterruptedException {
        MyRunnable t=new MyRunnable();
        Thread myThread=new Thread(t);
        myThread.start();
        // 这里sleep的就是main线程，而非myThread线程
        myThread.sleep(1000);
        Thread.sleep(10);
        System.out.println("Current Thread:"+Thread.currentThread().getName());
        for(int i=0;i<100;i++){
            System.out.println("main"+i);
        }
    }
}
