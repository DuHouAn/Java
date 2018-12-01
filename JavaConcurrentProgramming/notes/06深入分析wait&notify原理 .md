<!-- GFM-TOC -->
*  [深入分析wait&notify原理](#深入分析wait_notify原理)
    * [源码](#源码)
    * [用法](#用法)
    * [相关疑问](#相关疑问)
<!-- GFM-TOC -->
# 深入分析wait_notify原理

**Object作为Java中所有对象的基类，
其存在的价值不言而喻，其中wait&notify方法的实现多线程协作提供了保证**。

## 源码
其实说是两个方法，这两个方法包括他们的重载方法一共有 5 个，

而 Object 类中一共才 12 个方法，可见这 2 个方法的重要性。我们先看看 JDK 中的代码：

```java
public final native void notify();

public final native void notifyAll();

public final void wait() throws InterruptedException {
    wait(0);
}

public final native void wait(long timeout) throws InterruptedException;

public final void wait(long timeout, int nanos) throws InterruptedException {
    if (timeout < 0) {
        throw new IllegalArgumentException("timeout value is negative");
    }

    if (nanos < 0 || nanos > 999999) {
        throw new IllegalArgumentException(
                            "nanosecond timeout value out of range");
    }
    // 此处对于纳秒的处理不精准，只是简单增加了1毫秒，
    if (nanos > 0) {
        timeout++;
    }

    wait(timeout);
}
```
其中有 3 个方法是 native 的，也就是由虚拟机本地的 c 代码执行的。
有 2 个 wait 重载方法最终还是调用了 wait（long） 方法。

```java
wait方法：
wait是要释放对象锁，进入等待池。
既然是释放对象锁，那么肯定是先要获得锁。
所以wait必须要写在synchronized代码块中，否则会报异常。

notify方法：
也需要写在synchronized代码块中，调用对象的这两个方法也需要先获得该对象的锁。
notify，notifyAll，唤醒等待该对象同步锁的线程，并放入该对象的锁池中。
对象的锁池中线程可以去竞争得到对象锁，然后开始执行。
```

如果是通过notify来唤起的线程，那**先进入wait的线程会先被唤起来**，并非随机唤醒

如果是通过nootifyAll唤起的线程，默认情况是最后进入的会先被唤起来，即**LIFO的策略**

另外一点比较重要，notify，notifyAll调用时并不会释放对象锁。比如以下代码：
```java
public void test(){
    Object object = new Object();
    synchronized (object){
        object.notifyAll();
        while (true){
         
        }
    }
}
```
虽然调用了notifyAll，但是紧接着进入了一个死循环。
导致**一直不能出临界区，一直不能释放对象锁**。
所以，即使它把所有在等待池中的线程都唤醒放到了对象的锁池中，
但是锁池中的所有线程都不会运行，因为他们始终拿不到锁。

## 用法
```java
public class WaitNotifyExample {
    public static void main(String[] args) {
        final Object lock = new Object();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread A is waiting to get lock");
                synchronized (lock) {
                    try {
                        System.out.println("thread A get lock");
                        Thread.sleep(1000);
                        System.out.println("thread A do wait method");
                        lock.wait();
                        System.out.println("wait end");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread B is waiting to get lock");
                synchronized (lock) {
                    System.out.println("thread B get lock");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.notify();
                    System.out.println("thread B do notify method");
                }
            }
        }).start();
    }
}
```
```html
thread A is waiting to get lock
thread A get lock
thread B is waiting to get lock
thread A do wait method
thread B get lock
thread B do notify method
wait end
```
这里的前提就是，必须由同一个lock对象调用wait、notify方法

当线程A执行wait方法时，该线程会被挂起；
当线程B执行notify方法时，会唤醒一个被挂起的线程A；
lock对象、线程A和线程B三者是一种什么关系？根据上面的结论，可以想象一个场景：

lock对象维护了一个等待队列list；
线程A中执行lock的wait方法，把线程A保存到list中；
线程B中执行lock的notify方法，从等待队列中取出线程A继续执行。

## 相关疑问
(1)为何wait&notify必须要加synchronized锁？

从实现上来说，这个锁至关重要，正因为这把锁，才能让整个wait/notify玩转起来，
当然我觉得其实通过其他的方式也可以实现类似的机制，
不过**hotspot至少是完全依赖这把锁来实现wait/notify**的。

```java
static void Sort(int [] array) {
    // synchronize this operation so that some other thread can't
    // manipulate the array while we are sorting it. This assumes that other
    // threads also synchronize their accesses to the array.
    synchronized(array) {
        // now sort elements in array
    }
}
```
synchronized 代码块
通过javap生成的字节码中包含 monitor enter 和 monitor exit 指令。如下图所示：
<div align="center"> <img src="pics//thread/thread_11.png"/> </div>

执行 monitor enter 指令可以获取对象的monitor，
而 lock.wait() 方法通过调用native方法wait(0)实现，其中接口注释中有这么一句：

The current thread must own this object's monitor.

表示线程执行 lock.wait() 方法时，必须持有该lock对象的monitor，
如果wait方法在synchronized代码中执行，该线程很显然已经持有了monitor。

(2)为什么wait方法可能抛出InterruptedException异常?

当我们调用了某个线程的interrupt方法时，对应的线程会抛出这个异常，
wait方法也不希望破坏这种规则，因此就算当前线程因为wait一直在阻塞，
**当某个线程希望它起来继续执行的时候，它还是得从阻塞态恢复过来，
因此wait方法被唤醒起来的时候会去检测这个状态，
当有线程interrupt了它的时候，
它就会抛出这个异常从阻塞状态恢复过来**。

这里有两点要注意：
```java
1、如果被interrupt的线程只是创建了，并没有start，
那等他start之后进入wait态之后也是不能会恢复的

2、如果被interrupt的线程已经start了，在进入wait之前，
如果有线程调用了其interrupt方法，那这个wait等于什么都没做，
会直接跳出来，不会阻塞
```

(3)notify执行之后立马唤醒线程吗

其实hotspot里真正的实现是**退出同步块的时候才会去真正唤醒对应的线程**，
不过这个也是个默认策略，也可以改的，在notify之后立马唤醒相关线程。

(4)notifyAll是怎么实现全唤起所有线程

上面提到了当某个线程从wait状态恢复出来的时候，要先获取锁，然后再退出同步块，
所以notifyAll的实现是调用notify的线程在退出其同步块的时候唤醒起最后一个进入wait状态的线程，
然后这个线程退出同步块的时候继续唤醒其倒数第二个进入wait状态的线程，
依次类推，同样这是一个策略的问题，
JVM里提供了挨个直接唤醒线程的参数，不过都很罕见就不提了。

(5)wait的线程是否会影响load

这个或许是大家比较关心的话题，因为关乎系统性能问题，
wait/nofity 是通过JVM里的  park/unpark 机制来实现的，
在Linux下这种机制又是通过pthread_cond_wait/pthread_cond_signal 来玩的，
因此**当线程进入到wait状态的时候其实是会放弃cpu的，也就是说这类线程是不会占用cpu资源**。