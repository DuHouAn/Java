<!-- GFM-TOC -->
*  [Java线程同步与实现](#Java线程同步与实现)

<!-- GFM-TOC -->
# Java线程同步与实现
为何要使用Java线程同步？ 

Java允许多线程并发控制，当多个线程同时操作一个可共享的资源变量时，
将会导致数据不准确，相互之间产生冲突，因此加入同步锁以避免在该线程没有完成操作之前，
被其他线程的调用，从而保证了该变量的唯一性和准确性。

但其并发编程的根本，就是**使线程间进行正确的通信**。其中两个比较重要的关键点，如下：

```java
线程通信：重点关注线程同步的几种方式

正确通信：重点关注是否有线程安全问题
```
Java中提供了很多线程同步操作，
比如：synchronized关键字、wait/notifyAll、ReentrantLock、Condition、一些并发包下的工具类、
Semaphore，ThreadLocal、AbstractQueuedSynchronizer等。

## ReentrantLock可重入锁
自JDK5开始，新增了Lock接口以及它的一个实现类ReentrantLock。
ReentrantLock可重入锁是J.U.C包内置的一个锁对象，可以用来实现同步。
```java
public class LockThread implements Runnable{
    //ReentrantLock 是 java.util.concurrent包中的锁。
    private Lock lock=new ReentrantLock();
    private int count=0;

    @Override
    public void run() {
        lock.lock();//上锁
        try {
                for(int i=0;i<10;i++) {
                    System.out.println(Thread.currentThread().getName() + "==" + (count++));
                    Thread.sleep(100);
                }
        } catch (InterruptedException e) {
                e.printStackTrace();
        }finally {
            lock.unlock();//确保释放锁，从而避免发生死锁
        }
    }
}
```
```java
//一个线程运行完以后，另一个线程才运行
public class LockThreadTest {
    public static void main(String[] args) {
        LockThread lt=new LockThread();

        Thread t1=new Thread(lt);
        Thread t2=new Thread(lt);

        t1.start();
        t2.start();
    }
}
```
可重入锁中可重入表示的意义在于**对于同一个线程，可以继续调用加锁的方法，而不会被挂起**。
可重入锁内部维护一个计数器，对于同一个线程调用lock方法，计数器+1，调用unlock方法，计数器-1。

## synchronized
跟ReentrantLock一样，也支持可重入锁。
但是它是一个**关键字**，是一种语法级别的同步方式，称为内置锁。
```java
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
```
```java
public class SyncThreadTest {
    public static void main(String[] args) {
        SyncThread st=new SyncThread();

        Thread t1=new Thread(st);
        Thread t2=new Thread(st);

        t2.start();
        t1.start();
    }
}
```
同步是一种高开销的操作，因此应该尽量减少同步的内容。
通常没有必要同步整个方法，使用synchronized代码块同步关键代码即可。

synchronized跟ReentrantLock相比，有几点局限性：

(1)加锁的时候不能设置超时。
ReentrantLock有提供tryLock方法，可以设置超时时间，如果超过了这个时间并且没有获取到锁，
就会放弃，而synchronized却没有这种功能；

(2)ReentrantLock可以使用多个Condition，而synchronized却只能有1个

(3)不能中断一个试图获得锁的线程

(4)ReentrantLock可以选择公平锁和非公平锁

(5)ReentrantLock可以获得正在等待线程的个数，计数器等

所以，Lock的操作与synchronized相比，灵活性更高，而且Lock提供多种方式获取锁，
有Lock、ReadWriteLock接口，以及实现这两个接口的ReentrantLock类、ReentrantReadWriteLock类。

关于Lock对象和synchronized关键字选择的考量：
(1)最好两个都不用，使用一种java.util.concurrent包提供的机制，能够帮助用户处理所有与锁相关的代码。

(2)如果synchronized关键字能满足用户的需求，就用synchronized，因为它能简化代码。

(3)如果需要更高级的功能，就用ReentrantLock类，此时要注意及时释放锁，否则会出现死锁，通常在finally代码释放锁。

在性能考量上来说，如果竞争资源不激烈，两者的性能是差不多的，而当竞争资源非常激烈时（即有大量线程同时竞争），
此时Lock的性能要远远优于synchronized。所以说，在具体使用时要根据适当情况选择。

Reentrant和sunchronized的简单用法(以买票为例)：
```java
//未实现任何同步
public class SellTicket implements Runnable{
    private int tickets = 100; //这一百张票是线程的共有资源

    @Override
    public void run() {
        // 是为了模拟一直有票
        while (true) {
            if (tickets > 0) {
                // 为了模拟更真实的场景，我们稍作休息
                try {
                    Thread.sleep(100); //t1进来了并休息，t2进来了并休息，t3进来了并休息，
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "正在出售第" + (tickets--) + "张票");
            }
        }
    }
}
```
```java
//使用同步代码块，锁是任意对象
public class SellTicket2 implements Runnable{
    private int tickets = 100; //这一百张票是线程的共有资源

    //创建锁对象
    private Object obj = new Object();

    @Override
    public void run() {
        // 是为了模拟一直有票
        while (true) {
            synchronized (obj){ //同步代码块的锁是任何对象
                if (tickets > 0) {
                    // 为了模拟更真实的场景，我们稍作休息
                    try {
                        Thread.sleep(100); //t1进来了并休息，t2进来了并休息，t3进来了并休息，
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "正在出售第" + (tickets--) + "张票");
                }
            }
        }
    }
}
```
```java
//使用synchronized修饰方法，锁对象是当前对象
public class SellTicket3 implements Runnable{
    private int tickets = 100; //这一百张票是线程的共有资源

    //创建锁对象
    private Object obj = new Object();

    @Override
    public void run() {
        // 是为了模拟一直有票
        while (true) {
          sellTcicket();
        }
    }

    //锁对象是当前对象
    private synchronized void sellTcicket(){
        if (tickets > 0) {
            // 为了模拟更真实的场景，我们稍作休息
            try {
                Thread.sleep(100); //t1进来了并休息，t2进来了并休息，t3进来了并休息，
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "正在出售第" + (tickets--) + "张票");
        }
    }
}
```
```java
//使用用synchronized修饰静态方法，锁对象是Class对象
public class SellTicket4 implements Runnable{
    private static int tickets = 100; //这一百张票是线程的共有资源

    @Override
    public void run() {
        // 是为了模拟一直有票
        int x=0;
        while (true) {
            if(x%2==0){
                synchronized (SellTicket4.class){
                    sell();
                }
            }else{
                sellTicket(); //这里该同步静态方法的锁是 该类的字节码文件对象，即 SellTicket6.class
            }
            x++;
        }
    }

    private  static void sell(){
        if (tickets > 0) {
            // 为了模拟更真实的场景，我们稍作休息
            try {
                Thread.sleep(100); //t1进来了并休息，t2进来了并休息，t3进来了并休息，
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "正在出售第" + (tickets--) + "张票");
        }
    }

    private synchronized static void sellTicket(){
        //静态同步方法就是该类的	字节码文件对象
        sell();
    }
}
```
```java
//使用可重入锁进行同步
public class SellTicket5 implements Runnable{
    private static int tickets = 100; //这一百张票是线程的共有资源

    //创建锁对象
    private ReentrantLock locker=new ReentrantLock();

    @Override
    public void run() { //同步方法的锁是this
        // 是为了模拟一直有票
        while (true) {
            sell();
        }
    }

    private void sell(){
        try{
            locker.lock();
            if (tickets > 0) {
                // 为了模拟更真实的场景，我们稍作休息
                Thread.sleep(100); //t1进来了并休息，t2进来了并休息，t3进来了并休息，
                System.out.println(Thread.currentThread().getName() + "正在出售第" + (tickets--) + "张票");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            locker.unlock();
        }
    }
}
```