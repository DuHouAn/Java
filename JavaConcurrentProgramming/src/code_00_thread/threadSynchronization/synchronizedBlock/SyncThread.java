package code_00_thread.threadSynchronization.synchronizedBlock;

public class SyncThread implements Runnable{
    private static int count=0;

    @Override
    public void run() {
        synchronized(this){ //this指的是当前对象，这里作为锁对象
            //它只是作用于同一个对象，如果调用两个对象上的同步代码块，就不会进行同步。
            //当一个线程进入同步代码块，另一个线程就必须等待。
            for(int i=0;i<10;i++){
                try {
                    System.out.println(Thread.currentThread().getName()+"=="+(count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
       }
    }

    public int getCount(){
        return count;
    }
}
