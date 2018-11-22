package code_00_thread.threadLifeCycle;

/**
 * 线程的合并的含义就是将几个并行线程的线程合并为一个单线程执行，
 * 应用场景是当一个线程必须等待另一个线程执行完毕才能执行时，
 * Thread类提供了join方法来完成这个功能，注意，它不是静态方法。
 */
public class JoinMethod {
    public static void main(String[] args) throws InterruptedException {
        MyRunnable myRunnable=new MyRunnable();
        Thread t=new Thread(myRunnable);
        t.start();

        //将主线程加入到子线程后面,子线程执行完,主线程才能执行
        //t.join();

        //将主线程加入到子线程后面，不过如果子线程在1毫秒时间内没执行完，则主线程便不再等待它执行完
        t.join(100);

        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName() + "线程第" + i + "次执行！");
        }
    }
}
