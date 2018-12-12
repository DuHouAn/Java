package code_00_challengesOfConcurrentProgramming;

/**
 * 演示串行和并发执行并累加操作的时间
 *
 * 多线程一定快吗？
 * 多线程不一定快.这是因为线程有创建和上下文切换的开销。
 *
 *
 * TODO: 减少上下文切换的方法
 * (1)无锁并发编程
 * 多线程竞争锁时，会引起上下文切换，所以多线程处理数据时，可以用一 些办法来避免使用锁，
 * 如将数据的ID按照Hash算法取模分段，不同的线程处理不同段的数据。
 * (2)CAS算法
 * Java的Atomic包使用CAS算法来更新数据，而不需要加锁。
 * (3)使用最少线程
 * 避免创建不需要的线程，比如任务很少，但是创建了很多线程来处理，这样会造成大量线程都处于等待状态。
 * (4)使用协程。
 * 在单线程里实现多任务的调度，并在单线程里维持多个任务间的切换。
 *
 * 注意：
 * (1)协程：
 * Corroutines，是一种比线程更加轻量级的存在。
 * 一个线程可以拥有很多协程，协程不是被操作系统内核所管理，而完全是由程序所控制（也就是在用户态执行）。
 * 协程的暂停完全由程序控制，线程的阻塞状态是由操作系统内核来进行切换。
 * 因此，协程的开销远远小于线程的开销。
 *
 */
public class ConcurrencyTest {
    private static final long count = 100000001L;
    //private static final long count = 10000l;
    //private static final long count = 10000l;
    //private static final long count = 10000l;
    //private static final long count = 10000l;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    /**
     * 一个线程执行加操作
     * 一个线程执行减操作
     */
    private static void concurrency() throws InterruptedException {
        long start=System.currentTimeMillis();
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                int a=0;
                for(long i=0;i<count;i++){
                    a+=5;
                }
            }
        });
        t.start();
        int b=0;
        for(long i=0;i<count;i++){
            b--;
        }
        t.join();
        long end=System.currentTimeMillis();
        System.out.println("concurrency:" + (end-start)+"ms,b="+b);
    }

    /**
     * 串行执行加减
     */
    private static void serial(){
        long start=System.currentTimeMillis();
        int a=0;
        for(long i=0;i<count;i++){
            a+=5;
        }
        int b=0;
        for(long i=0;i<count;i++){
            b--;
        }
        long end=System.currentTimeMillis();
        System.out.println("serial:" + (end-start)+"ms,b="+b);
    }
}
