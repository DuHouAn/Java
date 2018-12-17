<!-- GFM-TOC -->
* [十、Lock解析](#Lock解析)
    * [concurrent包的结构层次](#concurrent包的结构层次)
    * [lock简介](#lock简介)
    * [Lock接口API](#Lock接口API)
    * [初识AQS](#初识AQS)
    * [AQS设计模式](#AQS设计模式)
<!-- GFM-TOC -->
# Lock解析
## concurrent包的结构层次
concurrent包的目录结构图:

<div align="center"> <img src="pics//09_00.png" width="400"/> </div><br>

其中包含了两个子包：atomic以及locks，
另外在concurrent下的阻塞队列以及Executors。

从整体上来看concurrent包的整体实现图如下图所示：

<div align="center"> <img src="pics//09_01.png" width="600"/> </div><br>

## lock简介
锁是用来控制多个线程访问共享资源的方式，一般来说，一个锁能够防止多个线程同时访问共享资源。
在Lock接口出现之前，java程序主要是靠synchronized关键字实现锁功能的，
而java SE5之后，并发包中增加了lock接口，它提供了与synchronized一样的锁功能。
虽然它失去了像synchronize关键字隐式加锁解锁的便捷性，但是却拥有了锁获取和释放的可操作性，
可中断的获取锁以及超时获取锁等多种synchronized关键字所不具备的同步特性。通常使用显示使用lock的形式如下：

```java
public void testLock () {
    lock.lock();
    try{
        ...
    } finally {
        lock.unlock();
    }
}
```
**synchronized同步块执行完成或者遇到异常是锁会自动释放，而lock必须调用unlock()方法释放锁，因此在finally块中释放锁**。

## Lock接口API
- Lock接口中定义的方法：
```java
void lock(); //获取锁 
void lockInterruptibly() throws InterruptedException；//获取锁的过程能够响应中断
boolean tryLock();//非阻塞式响应中断能立即返回，获取锁放回true,反之返回fasle 
boolean tryLock(long time, TimeUnit unit) throws InterruptedException;//超时获取锁，在超时内或者未中断的情况下能够获取锁
Condition newCondition();//获取与lock绑定的等待通知组件，当前线程必须获得了锁才能进行等待，进行等待时会先释放锁，当再次获取锁时才能从等待中返回
```
- ReentrantLock
```java
public class ReentrantLock implements Lock, java.io.Serializable
```
很显然ReentrantLock实现了lock接口，接下来我们来仔细研究一下它是怎样实现的。
当你查看源码时你会惊讶的发现ReentrantLock并没有多少代码，
另外有一个很明显的特点是：基本上所有的方法的实现实际上都是调用了其**静态内存类Sync**中的方法，
而Sync类继承了AbstractQueuedSynchronizer（AQS）。
可以看出要想理解ReentrantLock关键核心在于对队列同步器AbstractQueuedSynchronizer（简称同步器）的理解。

## 初识AQS
同步器是用来**构建锁和其他同步组件的基础框架**。

它的实现主要依赖一个int成员变量来表示同步状态以及通过一个FIFO队列构成等待队列。
它的子类必须重写AQS的几个protected修饰的用来改变同步状态的方法，其他方法主要是实现了排队和阻塞机制。
状态的更新使用**getState**,**setState**以及**compareAndSetState**这三个方法。

子类被推荐定义为自定义同步组件的静态内部类，同步器自身没有实现任何同步接口，
它仅仅是定义了若干同步状态的获取和释放方法来供自定义同步组件的使用，
同步器既支持**独占式获取同步状态**，
也可以支持**共享式获取同步状态**，
这样就可以方便的实现不同类型的同步组件。

同步器是实现锁（也可以是任意同步组件）的关键，在锁的实现中聚合同步器，利用同步器实现锁的语义。
可以这样理解二者的关系：

- 锁是面向使用者，它定义了使用者与锁交互的接口，隐藏了实现细节；

- 同步器是面向锁的实现者，它简化了锁的实现方式，屏蔽了同步状态的管理，线程的排队，等待和唤醒等底层操作。
锁和同步器很好的隔离了使用者和实现者所需关注的领域。

## AQS设计模式
AQS的设计是使用**模板方法设计模式**，它将一些方法开放给子类进行重写，
而同步器给同步组件所提供模板方法又会重新调用被子类所重写的方法。

比如AQS中需要重写的方法tryAcquire：
```java
protected boolean tryAcquire(int arg) {
          throw new UnsupportedOperationException();
  }
```

ReentrantLock中的NonfairSync（继承AQS）重写该方法为：

```java
protected final boolean tryAcquire(int acquires) {
    return nonfairTryAcquire(acquires);
}
```

对于AQS中的模板方法acquire():
```java
public final void acquire(int arg) {
      if (!tryAcquire(arg) &&
          acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
          selfInterrupt();
 }
```
该方法调用tryAcquire方法，
而此时当继承AQS的NonfairSync调用模板方法acquire时就会调用已经被NonfairSync重写的tryAcquire方法。
   
使用AQS的方法，可以归纳总结为这么几点：

- 同步组件（这里不仅仅值锁，还包括CountDownLatch等）的实现依赖于同步器AQS，
在同步组件实现中，使用AQS的方式被推荐定义继承AQS的静态内存类
- AQS采用模板方法进行设计，AQS的protected修饰的方法需要由继承AQS的子类进行重写实现，
当调用AQS的子类的方法时就会调用被重写的方法
- AQS负责同步状态的管理，线程的排队，等待和唤醒这些底层操作，而Lock等同步组件主要专注于实现同步语义
- 在重写AQS的方式时，使用AQS提供的getState(),setState(),compareAndSetState()方法进行修改同步状态

AQS提供的模板方法可以分为3类：

- 独占式获取与释放同步状态
- 共享式获取与释放同步状态
- 查询同步队列中等待线程情况
同步组件通过AQS提供的模板方法实现自己的同步语义

AQS的使用
```java
class Mutex implements Lock, java.io.Serializable{
    
}
```