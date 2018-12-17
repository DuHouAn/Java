<!-- GFM-TOC -->
* [十一、深入理解ReentrantLock](#深入理解ReentrantLock)
    * [ReentrantLock简介](#ReentrantLock简介)
    * [重入性的实现原理](#重入性的实现原理)
    * [公平锁与非公平锁](#公平锁与非公平锁)
<!-- GFM-TOC -->

# 深入理解ReentrantLock
## ReentrantLock简介
ReentrantLock重入锁，**是实现Lock接口的一个类**，也是在实际编程中使用频率很高的一个锁，
支持重入性，表示能够对共享资源能够重复加锁，即当前线程获取该锁再次获取不会被阻塞。
ReentrantLock还支持公平锁和非公平锁两种方式。
那么，要想完完全全的弄懂ReentrantLock的话，
主要也就是ReentrantLock同步语义的学习：

- 1. 重入性的实现原理
- 2. 公平锁和非公平锁

## 重入性的实现原理
要想支持重入性，就要解决两个问题：

- **1. 在线程获取锁的时候，如果已经获取锁的线程是当前线程的话则直接再次获取成功**

- 2. 由于锁会被获取n次，那么只有锁在被释放同样的n次之后，该锁才算是完全释放成功

针对第一个问题，我们来看看ReentrantLock是怎样实现的，
以非公平锁为例，判断当前线程能否获得锁为例，核心方法为nonfairTryAcquire(),源码如下：
```java
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    //1. 如果该锁未被任何线程占有，该锁能被当前线程获取
	if (c == 0) {
        if (compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
	//2.若被占有，检查占有线程是否是当前线程
    else if (current == getExclusiveOwnerThread()) {
		// 3. 再次获取，计数加一
        int nextc = c + acquires;
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```
为了支持重入性，在第二步增加了处理逻辑，如果该锁已经被线程所占有了，
会继续检查占有线程是否为当前线程，
如果是的话，同步状态加1返回true，表示可以再次获取成功。每次重新获取都会对同步状态进行加1的操作。

针对第二个问题，依然还是以非公平锁为例，核心方法为tryRelease，源码如下：
```java
protected final boolean tryRelease(int releases) {
	//1. 同步状态减1
    int c = getState() - releases;
    if (Thread.currentThread() != getExclusiveOwnerThread())
        throw new IllegalMonitorStateException();
    boolean free = false;
    if (c == 0) {
		//2. 只有当同步状态为0时，锁成功被释放，返回true
        free = true;
        setExclusiveOwnerThread(null);
    }
	// 3. 锁未被完全释放，返回false
    setState(c);
    return free;
}
```
重入锁的释放必须得等到同步状态为0时锁才算成功释放，否则锁仍未释放。
如果锁被获取n次，释放了n-1次，该锁未完全释放返回false，只有被释放n次才算成功释放，返回true。

## 公平锁与非公平锁
ReentrantLock支持两种锁：
- 公平锁
- 非公平锁

何谓公平性，是针对获取锁而言的，如果一个锁是公平的，那么锁的获取顺序就
应该**符合请求上的绝对时间顺序，满足FIFO**。
ReentrantLock的无参构造方法是构造非公平锁，源码如下：
```java
public ReentrantLock() {
    sync = new NonfairSync();
}
```
ReentrantLock的有参构造方法，传入一个boolean值，true时为公平锁，false时为非公平锁，源码如下：
```java
public ReentrantLock(boolean fair) {
    sync = fair ? new FairSync() : new NonfairSync();
}
```
公平锁的获取，tryAcquire()方法，源码如下：
```java
protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (!hasQueuedPredecessors() &&
            compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires;
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
  }
}
```
逻辑与nonfairTryAcquire基本上一致，
唯一的不同在于增加了hasQueuedPredecessors的逻辑判断，
方法名就可知道该方法用来判断当前节点在同步队列中是否有前驱节点的判断，
**如果有前驱节点说明有线程比当前线程更早的请求资源，根据公平性，当前线程请求资源失败**。
如果当前节点没有前驱节点的话，才有做后面的逻辑判断的必要性。
公平锁每次都是**从同步队列中的第一个节点获取到锁**，
而非公平性锁则不一定，有可能刚释放锁的线程能再次获取到锁。

公平锁与非公平锁的比较：

- 公平锁每次获取到锁为同步队列中的第一个节点，保证请求资源时间上的绝对顺序，
而非公平锁有可能刚释放锁的线程下次继续获取该锁，则有可能导致其他线程永远无法获取到锁，造成“饥饿”现象。

- 公平锁为了保证时间上的绝对顺序，需要频繁的上下文切换，
而非公平锁会降低一定的上下文切换，降低性能开销。因此，ReentrantLock默认选择的是非公平锁，则是为了减少一部分上下文切换，保证了系统更大的吞吐量。
