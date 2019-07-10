<!-- GFM-TOC -->
* [十四、Condition的await和signal等待通知机制](#Condition的await和signal等待通知机制)
    * [Condition简介](#Condition简介)
    * [Condition实现原理分析](#Condition实现原理分析)
        * [等待队列](#等待队列)
        * [await实现原理](#await实现原理)
        * [signal和signalAll实现原理](#signal和signalAll实现原理)
        * [await与signal和signalAll的结合](#await与signal和signalAll的结合)
<!-- GFM-TOC -->
# Condition的await和signal等待通知机制
## Condition简介
Object类是Java中所有类的父类，
在线程间实现通信的往往会应用到Object的几个方法：
wait(),wait(long timeout),wait(long timeout, int nanos)与notify(),notifyAll()
实现等待/通知机制，同样的， 在Java Lock体系下依然会有同样的方法实现等待/通知机制。
从整体上来看**Object的wait和notify/notify是与对象监视器配合完成线程间的等待/通知机制**，
**Condition与Lock配合完成等待/通知机制**，
**前者是Java底层级别的，后者是语言级别的，具有更高的可控制性和扩展性**。
两者除了在使用方式上不同外，在功能特性上还是有很多的不同:

- Condition能够支持不响应中断，而通过使用Object方式不支持
- Condition能够支持多个等待队列（new 多个Condition对象），而Object方式只能支持一个
- Condition能够支持超时时间的设置，而Object不支持

参照Object的wait和notify/notifyAll方法，Condition也提供了同样的方法：

- 针对Object的wait方法
```java
void await() throws InterruptedException
//当前线程进入等待状态，如果在等待状态中被中断会抛出被中断异常
long awaitNanos(long nanosTimeout)
//当前线程进入等待状态直到被通知，中断或者超时
boolean await(long time, TimeUnit unit)throws InterruptedException
//同第二种，支持自定义时间单位
boolean awaitUntil(Date deadline) throws InterruptedException
//当前线程进入等待状态直到被通知，中断或者到了某个时间
```

- 针对Object的notify/notifyAll方法
```java
void signal()
//唤醒一个等待在condition上的线程，将该线程从等待队列中转移到同步队列中，如果在同步队列中能够竞争到Lock则可以从等待方法中返回。
void signalAll()
//与1的区别在于能够唤醒所有等待在condition上的线程
```

## Condition实现原理分析
### 等待队列
创建一个Condition对象是通过lock.newCondition(),
而这个方法实际上是会创建ConditionObject对象，该类是AQS的一个内部类。
Condition是要和Lock配合使用的也就是Condition和Lock是绑定在一起的，而lock的实现原理又依赖于AQS，
自然而然ConditionObject作为AQS的一个内部类无可厚非。
我们知道在锁机制的实现上，AQS内部维护了一个**同步队列**，如果是独占式锁的话，
所有获取锁失败的线程的尾插入到同步队列，
同样的，Condition内部也是使用同样的方式，内部维护了一个**等待队列**，
所有调用condition.await方法的线程会加入到等待队列中，并且线程状态转换为等待状态。
另外注意到ConditionObject中有两个成员变量：
```java
/** First node of condition queue. */
private transient Node firstWaiter;
/** Last node of condition queue. */
private transient Node lastWaiter;
```
ConditionObject通过持有等待队列的头尾指针来管理等待队列。
注意Node类复用了在AQS中的Node类，Node类有这样一个属性：
```java
//后继节点
Node nextWaiter;
```
**等待队列是一个单向队列**，而在之前说AQS时知道**同步队列是一个双向队列**。

等待队列示意图：

<div align="center"> <img src="pics//13_00.png" width="600"/> </div><br>

注意：
我们可以多次调用lock.newCondition()方法创建多个Condition对象，也就是一个lLock可以持有多个等待队列。
利用Object的方式实际上是指在对象Object对象监视器上只能拥有一个同步队列和一个等待队列；
并发包中的Lock拥有一个同步队列和多个等待队列。示意图如下：

<div align="center"> <img src="pics//13_01.png" width="600"/> </div><br>

ConditionObject是AQS的内部类，
因此每个ConditionObject能够访问到AQS提供的方法，相当于每个Condition都拥有所属同步器的引用。

### await实现原理
**当调用condition.await()方法后会使得当前获取lock的线程进入到等待队列，
如果该线程能够从await()方法返回的话一定是该线程获取了与condition相关联的lock**。
await()方法源码如下：
```java
public final void await() throws InterruptedException {
    if (Thread.interrupted())
        throw new InterruptedException();
	// 1. 将当前线程包装成Node，尾插法插入到等待队列中
    Node node = addConditionWaiter();
	// 2. 释放当前线程所占用的lock，在释放的过程中会唤醒同步队列中的下一个节点
    int savedState = fullyRelease(node);
    int interruptMode = 0;
    while (!isOnSyncQueue(node)) {
		// 3. 当前线程进入到等待状态
        LockSupport.park(this);
        if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
            break;
    }
	// 4. 自旋等待获取到同步状态（即获取到lock）
    if (acquireQueued(node, savedState) && interruptMode != THROW_IE)
        interruptMode = REINTERRUPT;
    if (node.nextWaiter != null) // clean up if cancelled
        unlinkCancelledWaiters();
	// 5. 处理被中断的情况
    if (interruptMode != 0)
        reportInterruptAfterWait(interruptMode);
}
```
**当前线程调用condition.await()方法后，会使得当前线程释放lock然后加入到等待队列中，
直至被signal/signalAll后会使得当前线程从等待队列中移至到同步队列中去，
直到获得了lock后才会从await方法返回，或者在等待时被中断会做中断处理**。

addConditionWaiter()将当前线程添加到等待队列中,其源码如下：
```java
private Node addConditionWaiter() {
    Node t = lastWaiter;
    // If lastWaiter is cancelled, clean out.
    if (t != null && t.waitStatus != Node.CONDITION) {
        unlinkCancelledWaiters();
        t = lastWaiter;
    }
	//将当前线程包装成Node
    Node node = new Node(Thread.currentThread(), Node.CONDITION);
    if (t == null) //t==null,同步队列为空的情况
        firstWaiter = node;
    else
		//尾插法
        t.nextWaiter = node;
	//更新lastWaiter
    lastWaiter = node;
    return node;
}
```
这里通过尾插法将当前线程封装的Node插入到等待队列中，
同时可以看出**等待队列是一个不带头结点的链式队列**，之前我们学习AQS时知道**同步队列是一个带头结点的链式队列**。

将当前节点插入到等待对列之后，使用fullyRelease(0)方法释放当前线程释放lock，源码如下：
```java
final int fullyRelease(Node node) {
    boolean failed = true;
    try {
        int savedState = getState();
        if (release(savedState)) {
			//成功释放同步状态
            failed = false;
            return savedState;
        } else {
			//不成功释放同步状态抛出异常
            throw new IllegalMonitorStateException();
        }
    } finally {
        if (failed)
            node.waitStatus = Node.CANCELLED;
    }
}
```
调用**AQS的模板方法release()方法释放AQS的同步状态并且唤醒在同步队列中头结点的后继节点引用的线程**，
如果释放成功则正常返回，若失败的话就抛出异常。

如何从await()方法中退出？再看await()方法有这样一段代码：
```java
while (!isOnSyncQueue(node)) {
	// 3. 当前线程进入到等待状态
    LockSupport.park(this);
    if ((interruptMode = checkInterruptWhileWaiting(node)) != 0)
        break;
}
```
当线程第一次调用condition.await()方法时，
会进入到这个while()循环中，然后通过LockSupport.park(this)方法使得当前线程进入等待状态，
那么要想退出这个await方法就要先退出这个while循环，退出while循环的出口有2个：
- 1. break退出while循环
- 2. while循环中的逻辑判断为false

第1种情况的条件是**当前等待的线程被中断**后会走到break退出，

第2种情况是**当前节点被移动到了同步队列中**，（即另外线程调用的condition的signal或者signalAll方法），
while中逻辑判断为false后结束while循环。

当退出while循环后就会调用acquireQueued(node, savedState)，该方法的作用是
在自旋过程中线程不断尝试获取同步状态，直至成功（线程获取到lock）。

这样就说明了**退出await方法必须是已经获得了Condition引用（关联）的Lock**。

await方法示意图如下:

<div align="center"> <img src="pics//13_02.png" width="600"/> </div><br>

调用condition.await方法的线程必须是已经获得了lock，也就是当前线程是同步队列中的头结点。
调用该方法后会使得当前线程所封装的Node尾插入到等待队列中。

> **超时机制的支持**

condition还额外支持了超时机制，使用者可调用方法awaitNanos,awaitUtil。
这两个方法的实现原理，基本上与AQS中的tryAcquire方法如出一辙。

> **不响应中断的支持**

调用condition.awaitUninterruptibly()方法，该方法的源码为：
```java
public final void awaitUninterruptibly() {
    Node node = addConditionWaiter();
    int savedState = fullyRelease(node);
    boolean interrupted = false;
    while (!isOnSyncQueue(node)) {
        LockSupport.park(this);
        if (Thread.interrupted())
            interrupted = true;
    }
    if (acquireQueued(node, savedState) || interrupted)
        selfInterrupt();
}
```
与上面的await方法基本一致，只不过减少了对中断的处理，
并省略了reportInterruptAfterWait方法抛被中断的异常。

### signal和signalAll实现原理
调用Condition的signal或者signalAll方法可以将
**等待队列中等待时间最长的节点移动到同步队列中，使得该节点能够有机会获得lock**。
按照等待队列是先进先出（FIFO）的，
所以等待队列的头节点必然会是等待时间最长的节点，
也就是每次调用condition的signal方法是将头节点移动到同步队列中。
signal()源码如下：
```java
public final void signal() {
    //1. 先检测当前线程是否已经获取lock
    if (!isHeldExclusively())
        throw new IllegalMonitorStateException();
    //2. 获取等待队列中第一个节点，之后的操作都是针对这个节点
	Node first = firstWaiter;
    if (first != null)
        doSignal(first);
}
```
signal方法首先会检测当前线程是否已经获取lock，
如果没有获取lock会直接抛出异常，如果获取的话再得到等待队列的头指针引用的节点，doSignal方法也是基于该节点。
doSignal方法源码如下：
```java
private void doSignal(Node first) {
    do {
        if ( (firstWaiter = first.nextWaiter) == null)
            lastWaiter = null;
		//1. 将头结点从等待队列中移除
        first.nextWaiter = null;
		//2. while中transferForSignal方法对头结点做真正的处理
    } while (!transferForSignal(first) &&
             (first = firstWaiter) != null);
}
```
真正对头节点做处理的是transferForSignal()，该方法源码如下：
```java
final boolean transferForSignal(Node node) {
	//1. 更新状态为0
    if (!compareAndSetWaitStatus(node, Node.CONDITION, 0))
        return false;

	//2.将该节点移入到同步队列中去
    Node p = enq(node);
    int ws = p.waitStatus;
    if (ws > 0 || !compareAndSetWaitStatus(p, ws, Node.SIGNAL))
        LockSupport.unpark(node.thread);
    return true;
}
```
这段代码主要做了两件事情:
- 1.将头结点的状态更改为CONDITION
- 2.调用enq方法，将该节点尾插入到同步队列中

调用condition的signal的前提条件是
**当前线程已经获取了lock，该方法会使得等待队列中的头节点(等待时间最长的那个节点)移入到同步队列，
而移入到同步队列后才有机会使得等待线程被唤醒，
即从await方法中的LockSupport.park(this)方法中返回，从而才有机会使得调用await方法的线程成功退出**。

signal方法示意图如下:

<div align="center"> <img src="pics//13_03.png" width="600"/> </div><br>

> **signalAll**

sigllAll与sigal方法的区别体现在doSignalAll方法上。doSignalAll()的源码如下：
```java
private void doSignalAll(Node first) {
    lastWaiter = firstWaiter = null;
    do {
        Node next = first.nextWaiter;
        first.nextWaiter = null;
        transferForSignal(first);
        first = next;
    } while (first != null);
}
```
doSignal方法只会对等待队列的头节点进行操作，而doSignalAll方法将等待队列中的每一个节点都移入到同步队列中，
即“通知”当前调用condition.await()方法的每一个线程。

### await与signal和signalAll的结合
await和signal和signalAll方法就像一个开关控制着线程A（等待方）和线程B（通知方）。
它们之间的关系可以用下面一个图来表现得更加贴切：

<div align="center"> <img src="pics//13_04.png" width="600"/> </div><br>

线程awaitThread先通过lock.lock()方法获取锁成功后调用了condition.await方法进入等待队列，
而另一个线程signalThread通过lock.lock()方法获取锁成功后调用了condition.signal或者signalAll方法，
使得线程awaitThread能够有机会移入到同步队列中，
当其他线程释放lock后使得线程awaitThread能够有机会获取lock，
从而使得线程awaitThread能够从await方法中退出，然后执行后续操作。
如果awaitThread获取lock失败会直接进入到同步队列。