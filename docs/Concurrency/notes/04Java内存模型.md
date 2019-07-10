<!-- GFM-TOC -->
* [五、Java 内存模型](#五java-内存模型)
    * [主内存与工作内存](#主内存与工作内存)
    * [内存间交互操作](#内存间交互操作)
    * [指令重排序的条件](#指令重排序的条件)
    * [内存模型三大特性](#内存模型三大特性)
    * [先行发生原则](#先行发生原则(Happen-Before))
      <!-- GFM-TOC -->
# 五、Java 内存模型

Java 内存模型（JMM）是一种抽象的概念，并不真实存在，它描述了一组规则或规范，通过这组规范定义了程序中各个变量（包括实例字段、静态字段和构成数组对象的元素）的访问方式。试图屏蔽各种硬件和操作系统的内存访问差异，以实现让 Java 程序在各种平台下都能达到一致的内存访问效果。

> 注意JMM与JVM内存区域划分的区别：
>
> - JMM描述的是一组规则，围绕原子性、有序性和可见性展开；
> - 相似点：存在共享区域和私有区域

## 主内存与工作内存

处理器上的寄存器的读写的速度比内存快几个数量级，为了解决这种速度矛盾，在它们之间加入了高速缓存。

加入高速缓存带来了一个新的问题：缓存一致性。如果多个缓存共享同一块主内存区域，那么多个缓存的数据可能会不一致，需要一些协议来解决这个问题。

<div align="center"> <img src="pics//68778c1b-15ab-4826-99c0-3b4fd38cb9e9.png" width=""/> </div><br>

所有的变量都**存储在主内存中，每个线程还有自己的工作内存**，工作内存存储在高速缓存或者寄存器中，保存了该线程使用的变量的主内存副本拷贝。

线程只能直接操作工作内存中的变量，不同线程之间的变量值传递需要通过主内存来完成。

<div align="center"> <img src="pics//47358f87-bc4c-496f-9a90-8d696de94cee.png" width=""/> </div><br>

### 数据存储类型以及操作方式

- 方法中的基本类型本地变量将直接存储在工作内存的栈帧结构中；
- 引用类型的本地变量：引用存储在工作内存，实际存储在主内存；
- 成员变量、静态变量、类信息均会被存储在主内存中；
- 主内存共享的方式是线程各拷贝一份数据到工作内存中，操作完成后就刷新到主内存中。

## 内存间交互操作

Java 内存模型定义了 8 个操作来完成主内存和工作内存的交互操作。

<div align="center"> <img src="pics//536c6dfd-305a-4b95-b12c-28ca5e8aa043.png" width=""/> </div><br>

- read：把一个变量的值从主内存传输到工作内存中
- load：在 read 之后执行，把 read 得到的值放入工作内存的变量副本中
- use：把工作内存中一个变量的值传递给执行引擎
- assign：把一个从执行引擎接收到的值赋给工作内存的变量
- store：把工作内存的一个变量的值传送到主内存中
- write：在 store 之后执行，把 store 得到的值放入主内存的变量中
- lock：作用于主内存的变量
- unlock

## 指令重排序的条件

- 在单线程环境下不能改变程序的运行结果；
- 存在数据依赖关系的不允许重排序；
- 无法通过Happens-before原则推到出来的，才能进行指令的重排序。

## 内存模型三大特性

### 1. 原子性

Java 内存模型保证了 read、load、use、assign、store、write、lock 和 unlock 操作具有原子性，例如对一个 int 类型的变量执行 assign 赋值操作，这个操作就是原子性的。但是 Java 内存模型允许虚拟机将没有被 volatile 修饰的 64 位数据（long，double）的读写操作划分为两次 32 位的操作来进行，即 load、store、read 和 write 操作可以不具备原子性。

有一个错误认识就是，int 等原子性的类型在多线程环境中不会出现线程安全问题。前面的线程不安全示例代码中，cnt 属于 int 类型变量，1000 个线程对它进行自增操作之后，得到的值为 997 而不是 1000。

为了方便讨论，将内存间的交互操作简化为 3 个：load、assign、store。

下图演示了两个线程同时对 cnt 进行操作，load、assign、store 这一系列操作整体上看不具备原子性，那么在 T1 修改 cnt 并且还没有将修改后的值写入主内存，T2 依然可以读入旧值。可以看出，这两个线程虽然执行了两次自增运算，但是主内存中 cnt 的值最后为 1 而不是 2。因此对 int 类型读写操作满足原子性只是说明 load、assign、store 这些单个操作具备原子性。

<div align="center"> <img src="pics//ef8eab00-1d5e-4d99-a7c2-d6d68ea7fe92.png" width=""/> </div><br>

AtomicInteger 能保证多个线程修改的原子性。

<div align="center"> <img src="pics//952afa9a-458b-44ce-bba9-463e60162945.png" width=""/> </div><br>

使用 AtomicInteger 重写之前线程不安全的代码之后得到以下线程安全实现：

```java
public class AtomicExample {
    private AtomicInteger cnt = new AtomicInteger();

    public void add() {
        cnt.incrementAndGet();
    }

    public int get() {
        return cnt.get();
    }
}
```

```java
public static void main(String[] args) throws InterruptedException {
    final int threadSize = 1000;
    AtomicExample example = new AtomicExample(); // 只修改这条语句
    final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < threadSize; i++) {
        executorService.execute(() -> {
            example.add();
            countDownLatch.countDown();
        });
    }
    countDownLatch.await();
    executorService.shutdown();
    System.out.println(example.get());
}
```

```html
1000
```

除了使用原子类之外，也可以使用 synchronized 互斥锁来保证操作的原子性。它对应的内存间交互操作为：lock 和 unlock，在虚拟机实现上对应的字节码指令为 monitorenter 和 monitorexit。

```java
public class AtomicSynchronizedExample {
    private int cnt = 0;

    public synchronized void add() {
        cnt++;
    }

    public synchronized int get() {
        return cnt;
    }
}
```

```java
public static void main(String[] args) throws InterruptedException {
    final int threadSize = 1000;
    AtomicSynchronizedExample example = new AtomicSynchronizedExample();
    final CountDownLatch countDownLatch = new CountDownLatch(threadSize);
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < threadSize; i++) {
        executorService.execute(() -> {
            example.add();
            countDownLatch.countDown();
        });
    }
    countDownLatch.await();
    executorService.shutdown();
    System.out.println(example.get());
}
```

```html
1000
```

### 2. 可见性

可见性指当一个线程修改了共享变量的值，其它线程能够立即得知这个修改。Java 内存模型是通过在变量修改后将新值同步回主内存，在变量读取前从主内存刷新变量值来实现可见性的。JMM 内部的实现通常是依赖于所谓的**内存屏障**，通过**禁止某些重排序**的方式，提供内存**可见性保证**，也就是实现了**各种 happen-before 规则**。与此同时，更多复杂度在于，需要尽量确保各种编译器、各种体系结构的处理器，都能够提供一致的行为。

主要有有三种实现可见性的方式：

- volatile，会**强制**将该变量自己和当时其他变量的状态都**刷出缓存**。
- synchronized，对一个变量执行 unlock 操作之前，必须把变量值同步回主内存。
- final，被 final 关键字修饰的字段在构造器中一旦初始化完成，并且没有发生 this 逃逸（其它线程通过 this 引用访问到初始化了一半的对象），那么其它线程就能看见 final 字段的值。

对前面的线程不安全示例中的 cnt 变量使用 volatile 修饰，不能解决线程不安全问题，因为 volatile 并不能保证操作的原子性。

### 3. 有序性

有序性是指：在本线程内观察，所有操作都是有序的。在一个线程观察另一个线程，所有操作都是无序的，无序是因为发生了指令重排序。在 Java 内存模型中，允许编译器和处理器对指令进行重排序，重排序过程不会影响到单线程程序的执行，却会影响到多线程并发执行的正确性。

volatile 关键字通过添加内存屏障的方式来禁止指令重排，即重排序时不能把后面的指令放到内存屏障之前。

也可以通过 synchronized 来保证有序性，它保证每个时刻只有一个线程执行同步代码，相当于是让线程顺序执行同步代码。

## 先行发生原则(Happen-Before)

JSR-133内存模型使用先行发生原则在Java内存模型中保证多线程操作**可见性**的机制，也是对早期语言规范中含糊的可见性概念的一个精确定义。上面提到了可以用 volatile 和 synchronized 来保证有序性。除此之外，JVM 还规定了先行发生原则，让一个操作**无需控制**就能先于另一个操作完成。



由于**指令重排序**的存在，两个操作之间有happen-before关系，**并不意味着前一个操作必须要在后一个操作之前执行。**仅仅要求前一个操作的执行结果对于后一个操作是可见的，并且前一个操作**按顺序**排在第二个操作之前。

### 1. 单一线程原则（程序员顺序规则）

> Single Thread rule

在一个线程内，在程序前面的操作先行发生于后面的操作。

<div align="center"> <img src="pics//single-thread-rule.png" width=""/> </div><br>

### 2. 管程锁定规则（监视器锁规则）

> Monitor Lock Rule

一个 unlock（解锁） 操作**先行发生于**后面对同一个锁的 lock（加锁） 操作。

<div align="center"> <img src="pics//monitor-lock-rule.png" width=""/> </div><br>

### 3. volatile 变量规则

> Volatile Variable Rule

对一个 volatile 变量的**写操作**先行发生于后面对这个变量的**读操作**。

<div align="center"> <img src="pics//volatile-variable-rule.png" width=""/> </div><br>

### 4. 线程启动规则

> Thread Start Rule

Thread 对象的 **start()** 方法调用先行发生于此线程的每一个动作。

<div align="center"> <img src="pics//thread-start-rule.png" width=""/> </div><br>

### 5. 线程加入规则

> Thread Join Rule

Thread 对象的结束先行发生于 join() 方法返回。

<div align="center"> <img src="pics//thread-join-rule.png" width=""/> </div><br>

### 6. 线程中断规则

> Thread Interruption Rule

对线程 interrupt() 方法的调用先行发生于被中断线程的代码检测到中断事件的发生，可以通过 interrupted() 方法检测到是否有中断发生。

### 7. 对象终结规则

> Finalizer Rule

一个对象的初始化完成（构造函数执行结束）先行发生于它的 finalize() 方法的开始。

### 8. 传递性

> Transitivity

如果操作 A 先行发生于操作 B，操作 B 先行发生于操作 C，那么操作 A 先行发生于操作 C。