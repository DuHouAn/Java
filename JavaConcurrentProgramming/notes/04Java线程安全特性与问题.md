<!-- GFM-TOC -->
*  [Java线程安全特性与问题](#Java线程安全特性与问题)
    * [线程安全特性](#线程安全特性)
    * [线程安全问题](#线程安全问题)
<!-- GFM-TOC -->

# Java线程安全特性与问题
在单线程中不会出现线程安全问题，而在多线程编程中，
有可能会出现同时访问同一个共享、可变资源 的情况，
这种资源可以是：一个变量、一个对象、一个文件等。

特别注意两点：
```java
共享:意味着该资源可以由多个线程同时访问

可变:意味着该资源可以在其生命周期内被修改
```

简单的说，如果你的代码**在单线程下执行和在多线程下执行永远都能获得一样的结果**，
那么你的代码就是线程安全的。

## 线程安全特性
### 1、原子性
跟数据库事务的原子性概念差不多，
即一个操作（有可能包含有多个子操作）要么全部执行（生效），要么全部都不执行（都不生效）。

关于原子性，一个非常经典的例子就是银行转账问题：

```java
比如：A和B同时向C转账10万元。
如果转账操作不具有原子性，A在向C转账时，读取了C的余额为20万，
然后加上转账的10万，计算出此时应该有30万,
但还未来及将30万写回C的账户，此时B的转账请求过来了，
B发现C的余额为20万，然后将其加10万并写回。
然后A的转账操作继续——将30万写回C的余额。
这种情况下C的最终余额为30万，而非预期的40万。
```

### 2、可见性
**可见性是指，当多个线程并发访问共享变量时，
一个线程对共享变量的修改，其它线程能够立即看到**。
可见性问题是好多人忽略或者理解错误的一点。

CPU从主内存中读数据的效率相对来说不高，现在主流的计算机中，都有几级缓存。
**每个线程读取共享变量时，
都会将该变量加载进其对应CPU的高速缓存里，
修改该变量后，CPU会立即更新该缓存，
但并不一定会立即将其写回主内存（实际上写回主内存的时间不可预期）。
此时其它线程（尤其是不在同一个CPU上执行的线程）访问该变量时，从主内存中读到的就是旧的数据，
而非第一个线程更新后的数据**。

这一点是**操作系统或者说是硬件层面的机制**，所以很多应用开发人员经常会忽略。

### 3、有序性
有序性指的是，程序执行的顺序按照代码的先后顺序执行。以下面这段代码为例：

```java
boolean started = false; // 语句1
long counter = 0L; // 语句2
counter = 1; // 语句3
started = true; // 语句4
```
从代码顺序上看，上面四条语句应该依次执行，
但实际上JVM真正在执行这段代码时，并不保证它们一定完全按照此顺序执行。
处理器为了提高程序整体的执行效率，可能会对代码进行优化，
其中的一项优化方式就是调整代码顺序，按照更高效的顺序执行代码。
讲到这里，有人要着急了——什么，CPU不按照我的代码顺序执行代码，那怎么保证得到我们想要的效果呢？
实际上，大家大可放心，CPU虽然并不保证完全按照代码顺序执行，
但它会保证程序最终的执行结果和代码顺序执行时的结果一致。

- [原子性、可见性、有序性详情](https://github.com/DuHouAn/ConcurrencyNotes/blob/master/notes/%E7%BA%BF%E7%A8%8B%E5%AE%89%E5%85%A8%E6%80%A7.md)

## 线程安全问题
### 1、竞态条件与临界区
线程之间共享堆空间，在编程的时候就要格外注意避免竞态条件。

危险在于**多个线程同时访问相同的资源并进行读写操作**。
**当其中一个线程需要根据某个变量的状态来相应执行某个操作的之前，
该变量很可能已经被其它线程修改**。

```java
当两个线程竞争同一资源时，如果对资源的访问顺序敏感，就称存在竞态条件。

导致竟态条件发生的代码称作临界区。
```
```java
/**
 * 以下这段代码就存在竞态条件，其中return ++count就是临界区。
 */
public class Obj{
    private int count;

    public int incr(){
        return ++count;
    }
}
```
### 2、死锁
死锁：指两个或两个以上的进程（或线程）在执行过程中，
因争夺资源而造成的一种互相等待的现象，
若无外力作用，它们都将无法推进下去。
此时称系统处于死锁状态或系统产生了死锁，这些永远在互相等待的进程称为死锁进程。
    
关于死锁发生的条件：
    
(1)互斥条件：线程对资源的访问是排他性的，
如果一个线程对占用了某资源，那么其他线程必须处于等待状态，直到资源被释放。

(2)请求和保持条件：线程T1至少已经保持了一个资源R1占用，
但又提出对另一个资源R2请求，
而此时，资源R2被其他线程T2占用，
于是该线程T1也必须等待，
但又对自己保持的资源R1不释放。

(3)不剥夺条件：线程已获得的资源，
在未使用完之前，不能被其他线程剥夺，只能在使用完以后由自己释放。

(4)环路等待条件：在死锁发生时，必然存在一个“进程-资源环形链”，
即：{p0,p1,p2,...pn}，进程p0（或线程）等待p1占用的资源，
p1等待p2占用的资源，pn等待p0占用的资源。
（最直观的理解是，p0等待p1占用的资源，而p1而在等待p0占用的资源，
于是两个进程就相互等待）。

### 3、活锁
活锁是指线程1可以使用资源，但它很礼貌，让其他线程先使用资源，
线程2也可以使用资源，但它很绅士，也让其他线程先使用资源。
这样你让我，我让你，最后两个线程都无法使用资源。

死锁和活锁的例子：
```java
死锁：
迎面开来的汽车A和汽车B过马路，汽车A得到了半条路的资源
（满足死锁发生条件1：资源访问是排他性的，我占了路你就不能上来，除非你爬我头上去），
汽车B占了汽车A的另外半条路的资源，A想过去必须请求另一半被B占用的道路
（死锁发生条件2：必须整条车身的空间才能开过去，我已经占了一半，尼玛另一半的路被B占用了），
B若想过去也必须等待A让路，A是辆兰博基尼，B是开奇瑞QQ的屌丝，
A素质比较低开窗对B狂骂：快给老子让开，B很生气，你妈逼的，老子就不让
（死锁发生条件3：在未使用完资源前，不能被其他线程剥夺），
于是两者相互僵持一个都走不了
（死锁发生条件4：环路等待条件），
而且导致整条道上的后续车辆也走不了。

活锁：
马路中间有条小桥，只能容纳一辆车经过，桥两头开来两辆车A和B，
A比较礼貌，示意B先过，B也比较礼貌，示意A先过，
结果两人一直谦让谁也过不去。
```

### 4、饥饿
饥饿：是指如果线程T1占用了资源R，线程T2又请求封锁R，于是T2等待。
T3也请求资源R，当T1释放了R上的封锁后，系统首先批准了T3的请求，T2仍然等待。
然后T4又请求封锁R，当T3释放了R上的封锁之后，系统又批准了T4的请求......，
T2可能永远等待。

也就是，如果一个线程因为CPU时间全部被其他线程抢走而得不到CPU运行时间，
这种状态被称之为“饥饿”。
而该**线程被“饥饿致死”正是因为它得不到CPU运行时间的机会**。

关于“饥饿”的例子：
```java
在“首堵”北京的某一天，天气阴沉，空气中充斥着雾霾和地沟油的味道，
某个苦逼的临时工交警正在处理塞车，有两条道A和B上都堵满了车辆，
其中A道堵的时间最长，B相对堵的时间较短，
这时，前面道路已疏通，交警按照最佳分配原则，
示意B道上车辆先过，B道路上过了一辆又一辆，
A道上排队时间最长的却没法通过，
只能等B道上没有车辆通过的时候再等交警发指令让A道依次通过，
这也就是ReentrantLock显示锁里提供的不公平锁机制
（当然了，ReentrantLock也提供了公平锁的机制，由用户根据具体的使用场景而决定到底使用哪种锁策略），
**不公平锁能够提高吞吐量但不可避免的会造成某些线程的饥饿**。
```
在Java中，下面三个常见的原因会导致线程饥饿，如下：

(1)高优先级线程吞噬所有的低优先级线程的CPU时间
```java
你能为每个线程设置独自的线程优先级，优先级越高的线程获得的CPU时间越多，
线程优先级值设置在1到10之间，而这些优先级值所表示行为的准确解释则依赖于你的应用运行平台。
对大多数应用来说，你最好是不要改变其优先级值。
```

(2)线程被永久堵塞在一个等待进入同步块的状态，因为其他线程总是能在它之前持续地对该同步块进行访问
```java
Java的同步代码区也是一个导致饥饿的因素。
Java的同步代码区对哪个线程允许进入的次序没有任何保障。
这就意味着理论上存在一个试图进入该同步区的线程处于被永久堵塞的风险，
因为其他线程总是能持续地先于它获得访问，
这即是“饥饿”问题，而一个线程被“饥饿致死”正是因为它得不到CPU运行时间的机会。
```
(3)线程在等待一个本身（在其上调用wait()）也处于永久等待完成的对象，因为其他线程总是被持续地获得唤醒
```java
如果多个线程处在wait()方法执行上，而对其调用notify()不会保证哪一个线程会获得唤醒，
任何线程都有可能处于继续等待的状态。
因此存在这样一个风险：一个等待线程从来得不到唤醒，
因为其他等待线程总是能被获得唤醒。
```

### 5、公平
解决饥饿的方案被称之为“公平性” – 即所有线程均能公平地获得运行机会。
在Java中实现公平性方案，需要：
      
(1)使用锁，而不是同步块

(2)使用公平锁

(3)注意性能方面

在Java中实现公平性，虽Java不可能实现100%的公平性，
依然可以通过同步结构在线程间实现公平性的提高。
```java
public class Synchronizer{
    public synchronized void doSynchronized () {
        // do a lot of work which takes a long time
    }
}
```
如果有多个线程调用doSynchronized()方法，在第一个获得访问的线程未完成前，
其他线程将一直处于阻塞状态，而且在这种多线程被阻塞的场景下，
接下来将是哪个线程获得访问是没有保障的。

改为使用**锁方式替代同步块**，为了提高等待线程的公平性，
我们使用锁方式来替代同步块：
```java
public class Synchronizer{
    Lock lock = new Lock();
    public void doSynchronized() throws InterruptedException{
        this.lock.lock();
        //critical section, do a lot of work which takes a long time
        this.lock.unlock();
    }
}
```
注意到doSynchronized()不再声明为synchronized，
而是用lock.lock()和lock.unlock()来替代。

Lock类的实现：
```java
public class Lock{
    private boolean isLocked      = false;
    private Thread lockingThread = null;
    public synchronized void lock() throws InterruptedException{
        while(isLocked){
            wait();
        }

        isLocked = true;
        lockingThread = Thread.currentThread();
    }

    public synchronized void unlock(){
        if(this.lockingThread != Thread.currentThread()){
            throw new IllegalMonitorStateException("Calling thread has not locked this lock");
        }
        isLocked = false;
        lockingThread = null;
        notify();
    }
}
```
如果存在多线程并发访问lock()，这些线程将阻塞在对lock()方法的访问上。
另外，如果锁已经锁上（校对注：这里指的是isLocked等于true时），
这些线程将阻塞在while(isLocked)循环的wait()调用里面。
要记住的是，当线程正在等待进入lock() 时，
可以调用wait()释放其锁实例对应的同步锁，
使得其他多个线程可以进入lock()方法，并调用wait()方法。
同样当调用notify()时，wait()也不会做保障一定能唤醒线程。

**将上面Lock类转变为公平锁FairLock**
每一个调用lock()的线程都会进入一个队列，当解锁时，只有队列里的第一个线程被允许锁住FairLock实例，
所有其它的线程都将处于等待状态，直到他们处于队列头部。
```java
public class QueueObject {
    private boolean isNotified = false;

    public synchronized void doWait() throws InterruptedException {
        while(!isNotified){
            this.wait();
        }
        this.isNotified = false;
    }

    public synchronized void doNotify() {
        this.isNotified = true;
        this.notify();
    }

    public boolean equals(Object o) {
        return this == o;
    }
}
```
```java
public class FairLock {
    private boolean isLocked = false;
    private Thread lockingThread = null;
    private List<QueueObject> waitingThreads = new ArrayList<QueueObject>();

    public void lock() throws InterruptedException{
        // 当前线程创建“令牌”
        QueueObject queueObject = new QueueObject();
        boolean isLockedForThisThread = true;
        synchronized(this){
            // 所有线程的queueObject令牌，入队
            waitingThreads.add(queueObject);
        }

        while(isLockedForThisThread){
            synchronized(this){
                // 1. 判断是否已被锁住：是否已有线程获得锁，正在执行同步代码块
                // 2. 判断头部令牌与当前线程令牌是否一致：也就是只锁住头部令牌对应的线程；
                isLockedForThisThread = isLocked || waitingThreads.get(0) != queueObject;
                if(!isLockedForThisThread){
                    isLocked = true;
                    // 移除头部令牌
                    waitingThreads.remove(queueObject);
                    lockingThread = Thread.currentThread();
                    return;
                }
            }
            try{
                // 其他线程执行doWait()，进行等待
                queueObject.doWait();
            }catch(InterruptedException e){
                synchronized(this) { waitingThreads.remove(queueObject); }
                throw e;
            }
        }
    }

    public synchronized void unlock(){
        if(this.lockingThread != Thread.currentThread()){
            throw new IllegalMonitorStateException("Calling thread has not locked this lock");
        }
        isLocked = false;
        lockingThread = null;
        if(waitingThreads.size() > 0) {
            // 唤醒头部令牌对应的线程，可以执行
            waitingThreads.get(0).doNotify();
        }
    }
}
```
lock()方法不在声明为synchronized，
取而代之的是对必需同步的代码，在synchronized中进行嵌套。

FairLock新创建了一个QueueObject的实例，并对每个调用lock()的线程进行入队操作。
调用unlock()的线程将从队列头部获取QueueObject，
并对其调用doNotify()，以唤醒在该对象上等待的线程。
通过这种方式，**在同一时间仅有一个等待线程获得唤醒，而不是所有的等待线程**。
这也是实现FairLock公平性的核心所在。

queueObject.doWait()在try – catch块中是怎样调用的。
在InterruptedException抛出的情况下，线程得以离开lock()，并需让它从队列中移除。