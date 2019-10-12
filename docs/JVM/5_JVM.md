## 垃圾收集

[垃圾回收的脑图](http://naotu.baidu.com/file/1eb8ce88025d3d160c2efbf03c7b62b5?token=64d1a334774221b1)

垃圾收集主要是针对堆和方法区进行。程序计数器、虚拟机栈和本地方法栈这三个区域属于线程私有的，只存在于线程的生命周期内，线程结束之后就会消失，因此不需要对这三个区域进行垃圾回收。

### 判断一个对象是否可被回收

#### 1. 引用计数算法

为对象添加一个引用计数器，当对象增加一个引用时计数器加 1，引用失效时计数器减 1。引用计数为 0 的对象可被回收。

在两个对象出现循环引用的情况下，此时引用计数器永远不为 0，导致无法对它们进行回收。正是因为循环引用的存在，因此 Java 虚拟机不使用引用计数算法。

```java
public class Test {

    public Object instance = null;

    public static void main(String[] args) {
        Test a = new Test();
        Test b = new Test();
        a.instance = b;
        b.instance = a;
        a = null;
        b = null;
        doSomething();
    }
}
```

在上述代码中，a 与 b 引用的对象实例互相持有了对象的引用，因此当我们把对 a 对象与 b 对象的引用去除之后，由于两个对象还存在互相之间的引用，导致两个 Test 对象无法被回收。

- 优点：执行效率高，程序执行受影响较小。
- 缺点：无法检测出循环引用的情况，引起内存泄漏。

#### 2. 可达性分析算法

通过判断对象的引用链是否可达来决定对象是否可以被回收。

以 GC Roots 为起始点进行搜索，可达的对象都是存活的，不可达的对象可被回收。

Java 虚拟机使用该算法来判断对象是否可被回收，GC Roots 一般包含以下内容：

- 虚拟机栈中局部变量表中引用的对象（栈帧中的本地方法变量表）
- 本地方法栈中 JNI（Native方法） 中引用的对象
- 方法区中类静态属性引用的对象
- 方法区中的常量引用的对象
- 活跃线程的引用对象

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/83d909d2-3858-4fe1-8ff4-16471db0b180.png" width="350px"> </div>

#### 3. 方法区的回收

因为方法区主要存放永久代对象，而永久代对象的回收率比新生代低很多，所以在方法区上进行回收性价比不高。

主要是对常量池的回收和对类的卸载。

为了避免内存溢出，在大量使用反射和动态代理的场景都需要虚拟机具备类卸载功能。

类的卸载条件很多，需要满足以下三个条件，并且满足了条件也不一定会被卸载：

- 该类所有的实例都已经被回收，此时堆中不存在该类的任何实例。
- 加载该类的 ClassLoader 已经被回收。
- 该类对应的 Class 对象没有在任何地方被引用，也就无法在任何地方通过反射访问该类方法。

#### 4. finalize()

类似 C++ 的析构函数，用于关闭外部资源。但是 try-finally 等方式可以做得更好，并且该方法运行代价很高，不确定性大，无法保证各个对象的调用顺序，因此最好不要使用。

当一个对象可被回收时，如果需要执行该对象的 finalize() 方法，那么就有可能在该方法中让对象重新被引用，从而实现自救。自救只能进行一次，如果回收的对象之前调用了 finalize() 方法自救，后面回收时不会再调用该方法。

> Object 的finalize()方法的作用是否与C++的析构函数作用相同？

- 与C++的析构函数不同，析构函数调用确定，而finalize()方法是不确定的；
- 当垃圾回收器要宣告一个对象死亡时，至少要经历两次标记过程。如果对象在进行可达性分析以后，没有与GC Root直接相连接的引用量，就会被第一次标记，并且判断是否执行finalize()方法；如果这个对象覆盖了finalize()方法，并且未被引用，就会被放置于F-Queue队列，稍后由虚拟机创建的一个低优先级的finalize()线程去执行触发finalize()方法；
- 由于线程的优先级比较低，执行过程随时可能会被终止；
- 给予对象最后一次重生的机会

### 引用类型

无论是通过引用计数算法判断对象的引用数量，还是通过可达性分析算法判断对象是否可达，判定对象是否可被回收都与引用有关。

Java 提供了四种强度不同的引用类型。

#### 1. 强引用

被强引用关联的对象不会被回收。

使用 new 一个新对象的方式来创建强引用。

```java
Object obj = new Object();
```

抛出OOM Error终止程序也不会回收具有强引用的对象，只有通过将对象设置为null来弱化引用，才能使其被回收。

#### 2. 软引用

表示对象处在**有用但非必须**的状态。

被软引用关联的对象只有在内存不够的情况下才会被回收。可以用来实现内存敏感的高速缓存。

使用 SoftReference 类来创建软引用。

```java
Object obj = new Object();
SoftReference<Object> sf = new SoftReference<Object>(obj);
obj = null;  // 使对象只被软引用关联
```

#### 3. 弱引用

表示非必须的对象，比软引用更弱一些。适用于偶尔被使用且不影响垃圾收集的对象。

被弱引用关联的对象一定会被回收，也就是说它只能存活到下一次垃圾回收发生之前。

使用 WeakReference 类来创建弱引用。

```java
Object obj = new Object();
WeakReference<Object> wf = new WeakReference<Object>(obj);
obj = null;
```

#### 4. 虚引用

又称为幽灵引用或者幻影引用，一个对象是否有虚引用的存在，不会对其生存时间造成影响，也无法通过虚引用得到一个对象。

不会决定对象的生命周期，任何时候都可能被垃圾回收器回收。必须和引用队列ReferenceQueue联合使用。

为一个对象设置虚引用的唯一目的是**能在这个对象被回收时收到一个系统通知，起哨兵作用**。具体来说，就是通过判断引用队列ReferenceQueue是否加入虚引用来判断被引用对象是否被GC回收。

使用 PhantomReference 来创建虚引用。

```java
Object obj = new Object();
ReferenceQueue queue = new ReferenceQueue();
PhantomReference<Object> pf = new PhantomReference<Object>(obj, queue);
obj = null;
```

| 引用类型 | 被垃圾回收的时间 | 用途           | 生存时间          |
| -------- | ---------------- | -------------- | ----------------- |
| 强引用   | 从来不会         | 对象的一般状态 | JVM停止运行时终止 |
| 软引用   | 在内存不足的时候 | 对象缓存       | 内存不足时终止    |
| 弱引用   | 在垃圾回收的时候 | 对象缓存       | GC运行后终止      |
| 虚引用   | Unknown          | 标记、哨兵     | Unknown           |

> 引用队列（ReferenceQueue）：当GC（垃圾回收线程）准备回收一个对象时，如果发现它还仅有软引用(或弱引用，或虚引用)指向它，就会在回收该对象之前，把这个软引用（或弱引用，或虚引用）加入到与之关联的引用队列（ReferenceQueue）中。**如果一个软引用（或弱引用，或虚引用）对象本身在引用队列中，就说明该引用对象所指向的对象被回收了**。无实际的存储结构，存储逻辑依赖于内部节点之间的关系来表达。

### 垃圾收集算法

#### 1. 标记 - 清除

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/005b481b-502b-4e3f-985d-d043c2b330aa.png" width="400px"> </div>

在标记阶段，从根集合进行扫描，会检查每个对象是否为活动对象，如果是活动对象，则程序会在对象头部打上标记。

在清除阶段，会进行对象回收并取消标志位，另外，还会判断回收后的分块与前一个空闲分块是否连续，若连续，会合并这两个分块。回收对象就是把对象作为分块，连接到被称为 “空闲链表” 的单向链表，之后进行分配时只需要遍历这个空闲链表，就可以找到分块。

在分配时，程序会搜索空闲链表寻找空间大于等于新对象大小 size 的块 block。如果它找到的块等于 size，会直接返回这个分块；如果找到的块大于 size，会将块分割成大小为 size 与 (block - size) 的两部分，返回大小为 size 的分块，并把大小为 (block - size) 的块返回给空闲链表。

不足：

- 标记和清除过程效率都不高；
- 会产生大量不连续的内存碎片，导致无法给大对象分配内存。

#### 2. 标记 - 整理

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/ccd773a5-ad38-4022-895c-7ac318f31437.png" width="400px"> </div>

让所有存活的对象都向一端移动，然后直接清理掉端边界以外的内存。

优点:

- 不会产生内存碎片

不足:

- 需要移动大量对象，处理效率比较低。

#### 3. 复制

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/b2b77b9e-958c-4016-8ae5-9c6edd83871e.png" width="400px"> </div>

将内存划分为大小相等的两块，每次只使用其中一块，当这一块内存用完了就将还存活的对象复制到另一块上面，然后再把使用过的内存空间进行一次清理。

主要不足是只使用了内存的一半。

现在的商业虚拟机都采用这种收集算法回收新生代，但是并不是划分为大小相等的两块，而是一块较大的 Eden 空间和两块较小的 Survivor 空间，每次使用 Eden 和其中一块 Survivor。在回收时，将 Eden 和 Survivor 中还存活着的对象全部复制到另一块 Survivor 上，最后清理 Eden 和使用过的那一块 Survivor。

HotSpot 虚拟机的 Eden 和 Survivor 大小比例默认为 8:1，保证了内存的利用率达到 90%。如果每次回收有多于 10% 的对象存活，那么一块 Survivor 就不够用了，此时需要依赖于老年代进行空间分配担保，也就是借用老年代的空间存储放不下的对象。

#### 4. 分代收集

Stop-the-World

- JVM由于要执行GC而停止了应用程序的执行；
- 任何一种GC算法中都会发生；
- 多数GC优化通过减少Stop-the-world发生的时间来提升程序性能。

Safepoint

- 分析过程中对象引用关系不会发生变化的点；
- 产生Safepoint的地方：方法调用；循环跳转；异常跳转等

现在的商业虚拟机采用分代收集算法，它根据对象存活周期将内存划分为几块，不同块采用适当的收集算法。

一般将堆分为新生代和老年代。

- 新生代使用：复制算法
- 老年代使用：标记 - 清除 或者 标记 - 整理 算法

### 垃圾收集器

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/c625baa0-dde6-449e-93df-c3a67f2f430f.jpg" width=""/> </div>

以上是 HotSpot 虚拟机中的 7 个垃圾收集器，连线表示垃圾收集器可以配合使用。

- 单线程与多线程：单线程指的是垃圾收集器只使用一个线程，而多线程使用多个线程；
- 串行与并行：串行指的是垃圾收集器与用户程序交替执行，这意味着在执行垃圾收集的时候需要停顿用户程序；并行指的是垃圾收集器和用户程序同时执行。除了 CMS 和 G1 之外，其它垃圾收集器都是以串行的方式执行。

#### 1. Serial 收集器（-XX:+UseSerialGC）

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/22fda4ae-4dd5-489d-ab10-9ebfdad22ae0.jpg" width=""/> </div>

Serial 翻译为串行，也就是说它以串行的方式执行。

它是单线程的收集器，只会使用一个线程进行垃圾收集工作。

它的优点是简单高效，在单个 CPU 环境下，由于没有线程交互的开销，因此拥有最高的单线程收集效率。

它是 Client 场景下的默认新生代收集器，因为在该场景下内存一般来说不会很大。它收集一两百兆垃圾的停顿时间可以控制在一百多毫秒以内，只要不是太频繁，这点停顿时间是可以接受的。

#### 2. ParNew 收集器（-XX:+UseParNewGC）

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/81538cd5-1bcf-4e31-86e5-e198df1e013b.jpg" width=""/> </div>

它是 Serial 收集器的多线程版本。

它是 Server 场景下默认的新生代收集器，除了性能原因外，主要是因为除了 Serial 收集器，只有它能与 CMS 收集器配合使用。

#### 3. Parallel Scavenge 收集器（-XX:+UseParallelGC）

与 ParNew 一样是多线程收集器。

其它收集器目标是尽可能缩短垃圾收集时用户线程的停顿时间，而它的目标是达到一个可控制的吞吐量，因此**它被称为“吞吐量优先”收集器**。这里的吞吐量指 CPU 用于运行用户程序的时间占总时间的比值。

停顿时间越短就越适合需要与用户交互的程序，良好的响应速度能提升用户体验。而高吞吐量则可以高效率地利用 CPU 时间，尽快完成程序的运算任务，适合在后台运算而不需要太多交互的任务。

缩短停顿时间是以牺牲吞吐量和新生代空间来换取的：新生代空间变小，垃圾回收变得频繁，导致吞吐量下降。

可以通过一个开关参数打开 GC 自适应的调节策略（GC Ergonomics），就不需要手工指定新生代的大小（-Xmn）、Eden 和 Survivor 区的比例、晋升老年代对象年龄等细节参数了。虚拟机会根据当前系统的运行情况收集性能监控信息，动态调整这些参数以提供最合适的停顿时间或者最大的吞吐量。

#### 4. Serial Old 收集器（-XX:+UseSerialOldGC）

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/08f32fd3-f736-4a67-81ca-295b2a7972f2.jpg" width=""/> </div>

是 Serial 收集器的老年代版本，也是给 Client 场景下的虚拟机使用。如果用在 Server 场景下，它有两大用途：

- 在 JDK 1.5 以及之前版本（Parallel Old 诞生以前）中与 Parallel Scavenge 收集器搭配使用。
- 作为 CMS 收集器的后备预案，在并发收集发生 Concurrent Mode Failure 时使用。

#### 5. Parallel Old 收集器（-XX:+UseParallelOldGC）

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/278fe431-af88-4a95-a895-9c3b80117de3.jpg" width=""/> </div>

是 Parallel Scavenge 收集器的老年代版本。

**在注重吞吐量以及 CPU 资源敏感的场合**，都可以优先考虑 Parallel Scavenge 加 Parallel Old 收集器。

#### 6. CMS 收集器（-XX:+UseConcMarkSweepGC）

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/62e77997-6957-4b68-8d12-bfd609bb2c68.jpg" width=""/> </div>

CMS（Concurrent Mark Sweep），Mark Sweep 指的是标记 - 清除算法。

分为以下六个流程：

- 初始标记：仅仅只是标记一下 GC Roots 能直接关联到的对象，速度很快，需要停顿。
- 并发标记：进行 GC Roots Tracing 的过程，它在整个回收过程中耗时最长，不需要停顿。
- 并发预清理：查找执行并发标记阶段从年轻代晋升到老年代的对象
- **重新标记**：为了修正并发标记期间因用户程序继续运作而导致标记产生变动的那一部分对象的标记记录，需要停顿。
- 并发清除：清理垃圾对象，不需要停顿。
- 并发重置：重置CMS收集器的数据结构，等待下一次垃圾回收。

在整个过程中耗时最长的并发标记和并发清除过程中，收集器线程都可以与用户线程一起工作，不需要进行停顿。

具有以下缺点：

- 吞吐量低：低停顿时间是以牺牲吞吐量为代价的，导致 CPU 利用率不够高。
- 无法处理浮动垃圾，可能出现 Concurrent Mode Failure。浮动垃圾是指并发清除阶段由于用户线程继续运行而产生的垃圾，这部分垃圾只能到下一次 GC 时才能进行回收。由于浮动垃圾的存在，因此需要预留出一部分内存，意味着 CMS 收集不能像其它收集器那样等待老年代快满的时候再回收。如果预留的内存不够存放浮动垃圾，就会出现 Concurrent Mode Failure，这时虚拟机将临时启用 Serial Old 来替代 CMS。
- 标记 - 清除算法导致的空间碎片，往往出现老年代空间剩余，但无法找到足够大连续空间来分配当前对象，不得不提前触发一次 Full GC。

#### 7. G1 收集器（-XX:+UseG1GC）

G1（Garbage-First），它是一款面向服务端应用的垃圾收集器，在多 CPU 和大内存的场景下有很好的性能。HotSpot 开发团队赋予它的使命是未来可以替换掉 CMS 收集器。

堆被分为新生代和老年代，其它收集器进行收集的范围都是整个新生代或者老年代，而 G1 可以直接对新生代和老年代一起回收。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/4cf711a8-7ab2-4152-b85c-d5c226733807.png" width="600"/> </div>

G1 把堆划分成多个大小相等的独立区域（Region），新生代和老年代不再物理隔离。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/9bbddeeb-e939-41f0-8e8e-2b1a0aa7e0a7.png" width="600"/> </div>

通过引入 Region 的概念，从而将原来的一整块内存空间划分成多个的小空间，使得每个小空间可以单独进行垃圾回收。这种划分方法带来了很大的灵活性，使得可预测的停顿时间模型成为可能。通过记录每个 Region 垃圾回收时间以及回收所获得的空间（这两个值是通过过去回收的经验获得），并维护一个优先列表，每次根据允许的收集时间，优先回收价值最大的 Region。

每个 Region 都有一个 Remembered Set，用来记录该 Region 对象的引用对象所在的 Region。通过使用 Remembered Set，在做可达性分析的时候就可以避免全堆扫描。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/f99ee771-c56f-47fb-9148-c0036695b5fe.jpg" width=""/> </div>

如果不计算维护 Remembered Set 的操作，G1 收集器的运作大致可划分为以下几个步骤：

- 初始标记
- 并发标记
- 最终标记：为了修正在并发标记期间因用户程序继续运作而导致标记产生变动的那一部分标记记录，虚拟机将这段时间对象变化记录在线程的 Remembered Set Logs 里面，最终标记阶段需要把 Remembered Set Logs 的数据合并到 Remembered Set 中。这阶段需要停顿线程，但是可并行执行。
- 筛选回收：首先对各个 Region 中的回收价值和成本进行排序，根据用户所期望的 GC 停顿时间来制定回收计划。此阶段其实也可以做到与用户程序一起并发执行，但是因为只回收一部分 Region，时间是用户可控制的，而且停顿用户线程将大幅度提高收集效率。

具备如下特点：

- 并行和并发
- 分代收集
- 空间整合：整体来看是基于“标记 - 整理”算法实现的收集器，从局部（两个 Region 之间）上来看是基于“复制”算法实现的，这意味着运行期间不会产生内存空间碎片。
- 可预测的停顿：能让使用者明确指定在一个长度为 M 毫秒的时间片段内，消耗在 GC 上的时间不得超过 N 毫秒。



### 降低停顿时间

#### 1. 使用 CMS 收集器

CMS 收集器进行垃圾回收，有 4 个步骤：

- 初始标记
- 并发标记
- 重新标记
- 并发清除

其中初始标记和重新标记需要 "stop the world"，但耗时时间最长的并发标记、并发清除过程中，GC 线程都可与用户线程一起工作。整体上说，CMS 和用户线程是并行的。

#### 2. 增量算法

基本思路：若一次性将所有垃圾进行处理，会造成系统长时间的停顿，则就让 GC 线程与用户线程交替执行。每次 GC 线程只收集一小块区域的内存空间，接着切换到用户线程，重复几次，直至 GC 完成。

问题：存在线程切换和上下文切换，造成系统吞吐量下降。

