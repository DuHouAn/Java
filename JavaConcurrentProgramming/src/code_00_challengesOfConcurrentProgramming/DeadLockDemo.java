package code_00_challengesOfConcurrentProgramming;

/**
 * 锁是个非常有用的工具，运用场景非常多，因为它使用起来非常简单，而且易于理解。
 *
 * TODO:避免死锁的几个常见方法
 ·(1)避免一个线程同时获取多个锁。
 ·(2)避免一个线程在锁内同时占用多个资源，TODO:尽量保证每个锁只占用一个资源。
 ·(3)尝试使用定时锁，使用lock.tryLock（timeout）来替代使用内部锁机制。
 ·(4)对于数据库锁，加锁和解锁必须在一个数据库连接里，否则会出现解锁失败的情况。
 *
 * 注意：
 * (1)java锁机制
 * 当多个线程对同一个共享变量/对象进行操作，即使是最简单的操作，
 * 如：i++，在处理上实际也涉及到读取、自增、赋值这三个操作，也就是说 ,
 * 、这中间存在时间差，导致多个线程没有按照如程序编写者所设想的去顺序执行，出现错位，从而导致最终结果与预期不一致。
 *
 * Java中的多线程同步是通过锁的概念来体现。
 * TODO:锁不是一个对象、不是一个具体的东西，而是一种机制的名称.
 * 锁机制需要保证如下两种特性：
 * i.互斥性（原子性）：
 * TODO:在同一时间只允许一个线程持有某个对象锁，
 * 通过这种特性来实现多线程中的协调机制，
 * 这样在同一时间只有一个线程对需同步的代码块(复合操作)进行访问。
 * ii.可见性：
 * TODO:必须确保在锁被释放之前，对共享变量所做的修改，
 * 对于随后获得该锁的另一个线程是可见的（即在获得锁时应获得最新共享变量的值），
 * 否则另一个线程可能是在本地缓存的某个副本上继续操作从而引起不一致。
 */

/**
 * 一旦产生死锁，就会造成系统功能不可用。
 * 线程t1和线程t2互相等待对方释放锁。
 */
public class DeadLockDemo {
    private static String A = "A";
    private static String B = "B";
    public static void main(String[] args) {
        new DeadLockDemo().deadLock();
        System.out.println("over");
    }

    public void deadLock(){
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A){
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B){
                        System.out.println("B");
                    }
                }
            }
        });
        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B){
                    synchronized (A){
                        System.out.println("A");
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
}
