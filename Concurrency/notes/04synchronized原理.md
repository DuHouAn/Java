<!-- GFM-TOC -->
* [五、synchronized原理](#五synchronized原理)
    * [synchronized基本原理](#synchronized基本原理)
        * [对象锁（monitor）机制](#1.对象锁（monitor）机制)
        * [synchronized的happens-before关系](#2.synchronized的happens-before关系)
        * [锁获取和锁释放的内存语义](#3.锁获取和锁释放的内存语义)
    * [synchronized优化](#synchronized优化)
        * [CAS操作](#CAS操作)
        * [轻量级锁](#轻量级锁)
        * [偏向锁](#偏向锁)
        * [各种锁的比较](#各种锁的比较)
<!-- GFM-TOC -->

# 五、synchronized原理
java代码中使用synchronized可是使用在代码块和方法中，根据Synchronized用的位置可以有这些使用场景：

<div align="center"> <img src="pics//04_00.png" width="600"/> </div><br>

这里的需要注意的是：**如果锁的是类对象的话，尽管new多个实例对象，但他们仍然是属于同一个类依然会被锁住，即线程之间保证同步关系**。

## synchronized基本原理
### 1.对象锁（monitor）机制
先写一个简单的Demo:
```java
public class SynchronizedDemo {
    public static void main(String[] args) {
        synchronized (SynchronizedDemo.class) {
        }
        method();
    }

    private synchronized  static void method() {
    }
}
```

上面的代码中有一个同步代码块，锁住的是类对象，并且还有一个同步静态方法，锁住的依然是该类的类对象。
编译之后，切换到SynchronizedDemo.class的同级目录之后，然后用
javap -v SynchronizedDemo.class查看字节码文件：

<div align="center"> <img src="pics//04_01.png" width="600"/> </div><br>

执行同步代码块后首先要先执行**monitorenter指令**，**退出的时候monitorexit指令**。

通过分析之后可以看出，使用Synchronized进行同步，其关键就是必须要对对象的监视器monitor进行获取，
当线程获取monitor后才能继续往下执行，否则就只能等待。
而这个获取的过程是**互斥**的，即同一时刻只有一个线程能够获取到monitor。

上面的Demo中在执行完同步代码块之后紧接着再会去执行一个静态同步方法，而这个方法锁的对象依然就这个类对象，
那么这个正在执行的线程还需要获取该锁吗？答案是不必的，从上图中就可以看出来，
执行静态同步方法的时候就只有一条monitorexit指令，并没有monitorenter获取锁的指令。
这就是**锁的重入性**，
即在同一线程中，线程不需要再次获取同一把锁。
synchronized先天具有重入性。
**每个对象拥有一个计数器，当线程获取该对象锁后，计数器就会加一，释放锁后就会将计数器减一**。

任意一个对象都拥有自己的监视器，当这个对象由同步块或者这个对象的同步方法调用时，
执行方法的线程必须先获取该对象的监视器才能进入同步块和同步方法，
如果没有获取到监视器的线程将会被阻塞在同步块和同步方法的入口处，进入到BLOCKED状态。

下图表现了对象，对象监视器，同步队列以及执行线程状态之间的关系：

<div align="center"> <img src="pics//04_02.png" width="600"/> </div><br>

任意线程对Object的访问，**首先要获得Object的监视器**，
如果获取失败，该线程就进入同步状态，线程状态变为BLOCKED，
当Object的监视器占有者释放后，在**同步队列**中得线程就会有机会重新获取该监视器。

### 2.synchronized的happens-before关系
Synchronized的happens-before规则，即监视器锁规则：

对同一个监视器的解锁，happens-before于对该监视器的加锁。

继续来看代码：
```java
public class MonitorDemo {
    private int a = 0;

    public synchronized void writer() {     // 1
        a++;                                // 2
    }                                       // 3

    public synchronized void reader() {    // 4
        int i = a;                         // 5
    }                                      // 6
}
```
happens-before关系如图所示：

<div align="center"> <img src="pics//04_03.png" width="600"/> </div><br>

在图中每一个箭头连接的两个节点就代表之间的happens-before关系：

- 黑色的是通过程序顺序规则推导出来
- 红色的为监视器锁规则推导而出：线程A释放锁happens-before线程B加锁
- 蓝色的则是通过程序顺序规则和监视器锁规则推测出来happens-before关系，通过传递性规则进一步推导的happens-before关系。

现在我们来重点关注2 happens-before 5，通过这个关系我们可以得出什么？

根据happens-before的单一线程原则(在一个线程内，在程序前面的操作先行发生于后面的操作):
如果A happens-before B，则A的执行结果对B可见，并且A的执行顺序先于B。
线程A先对共享变量A进行加一，由2 happens-before 5关系可知线程A的执行结果对线程B可见即线程B所读取到的a的值为1。

### 3.锁获取和锁释放的内存语义
基于java内存抽象模型的Synchronized的内存语义:

<div align="center"> <img src="pics//04_04.png" width="600"/> </div><br>

线程A会首先先从主内存中读取共享变量a=0的值然后将该变量拷贝到自己的本地内存，
进行加一操作后，再将该值刷新到主内存，整个过程即:

线程A加锁-->执行临界区代码-->释放锁相对应的内存语义

<div align="center"> <img src="pics//04_05.png" width="600"/> </div><br>

线程B获取锁的时候同样会从主内存中获取共享变量a的值，这个时候就是最新的值1,
然后将该值拷贝到线程B的工作内存中去，释放锁的时候同样会重写到主内存中。

**从整体上来看，线程A的执行结果（a=1）对线程B是可见的**。实现原理为：

释放锁的时候会将值刷新到主内存中，其他线程获取锁时会强制从主内存中获取最新的值。

另外也验证了2 happens-before 5，2的执行结果对5是可见的。

**从横向来看，这就像线程A通过主内存中的共享变量和线程B进行通信**。

A 告诉 B 我们俩的共享数据现在为1啦，这种线程间的通信机制正好吻合java的内存模型正好是共享内存的并发模型结构。

## synchronized优化
### CAS操作
### 轻量级锁
### 偏向锁
### 各种锁的比较