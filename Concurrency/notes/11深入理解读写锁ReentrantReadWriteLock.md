<!-- GFM-TOC -->
* [十二、深入理解读写锁ReentrantReadWriteLock](#深入理解读写锁ReentrantReadWriteLock)
    * [读写锁简介](#读写锁简介)
    * [写锁详解](#写锁详解)
        * [写锁的获取](#写锁的获取)
        * [写锁的释放](#写锁的释放)
    * [读锁详解](#读锁详解)
        * [读锁的获取](#读锁的获取)
        * [读锁的释放](#读锁的释放)
    * [锁降级](#锁降级)
<!-- GFM-TOC -->
# 深入理解读写锁ReentrantReadWriteLock
## 读写锁简介
在并发场景中用于解决线程安全的问题，我们几乎会高频率的使用到独占式锁，
通常使用java提供的关键字synchronized或者concurrents包中实现了Lock接口的ReentrantLock。
它们都是**独占式获取锁，也就是在同一时刻只有一个线程能够获取锁**。

在一些业务场景中，大部分只是读数据，写数据很少，如果仅仅是读数据的话并不会影响数据正确性（出现脏读），
而如果在这种业务场景下，依然使用独占锁的话，很显然这将是出现性能瓶颈的地方。

针对这种**读多写少**的情况，
java还提供了另外一个实现Lock接口的ReentrantReadWriteLock(读写锁)。
**读写锁允许同一时刻被多个读线程访问，但是在写线程访问时，所有的读线程和其他的写线程都会被阻塞**。

了解几个概念：

- 公平性选择：支持非公平性（默认）和公平的锁获取方式
- 重入性：支持重入，读锁获取后能再次获取，写锁获取之后能够再次获取写锁，同时也能够获取读锁
- 锁降级：遵循**先获取写锁、获取读锁再释放写锁**的次序，写锁能够降级成为读锁

要想能够彻底的理解读写锁必须能够理解这样几个问题：

- 1. 读写锁是怎样实现分别记录读写状态的？
- 2. 写锁是怎样获取和释放的？
- 3.读锁是怎样获取和释放的？

## 写锁详解
### 写锁的获取
在同一时刻写锁是不能被多个线程所获取，很显然写锁是**独占式锁**，
而实现写锁的同步语义是通过重写AQS中的tryAcquire方法实现的。
tryAcquire()源码如下：
```java
@Override
protected final boolean tryAcquire(int acquires) {
    Thread current = Thread.currentThread();
	// 1. 获取写锁当前的同步状态
    int c = getState();
	// 2. 获取写锁获取的次数
    int w = exclusiveCount(c);
    if (c != 0) {
        // (Note: if c != 0 and w == 0 then shared count != 0)
		// 3.1 当读锁已被读线程获取或者当前线程不是已经获取写锁的线程的话
		// 当前线程获取写锁失败
        if (w == 0 || current != getExclusiveOwnerThread())
            return false;
        //exclusiveCount()写锁的获取次数
        if (w + exclusiveCount(acquires) > MAX_COUNT)
            throw new Error("Maximum lock count exceeded");
        // Reentrant acquire
		// 3.2 当前线程获取写锁，支持可重复加锁
        setState(c + acquires);
        return true;
    }
	// 3.3 写锁未被任何线程获取，当前线程可获取写锁
    if (writerShouldBlock() ||
        !compareAndSetState(c, c + acquires))
        return false;
    setExclusiveOwnerThread(current);
    return true;
}
```
exclusiveCount(c)方法，该方法源码如下：
```java
//EXCLUSIVE_MASK为1左移16位然后减1，即为0x0000FFFF
static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1; 
static int exclusiveCount(int c) { 
    return c & EXCLUSIVE_MASK; 
}
```
exclusiveCount()方法是将同步状态（state为int类型）与0x0000FFFF相与，即取同步状态的低16位。
而**同步状态的低16位用来表示写锁的获取次数**。

还有一个方法sharedCount(int),该方法源码如下：
```java
static int sharedCount(int c){
    return c >>> SHARED_SHIFT; 
}
```
方法是获取读锁被获取的次数，是将同步状态c右移16位，即取同步状态的高16位，
而**同步状态的高16位用来表示读锁被获取的次数**。

这里第一个问题即"读写锁是怎样实现分别记录读写状态的？"就解决了。

<div align="center"> <img src="pics//11_00.png" width="600"/> </div><br>

> 小结

写锁获取方法tryAcquire()，其主要逻辑：
**当读锁已经被读线程获取或者写锁已经被其他写线程获取，则写锁获取失败；否则，获取成功并支持重入，增加写状态**。

### 写锁的释放
写锁释放通过重写AQS的tryRelease()方法，源码如下：
```java
@Override
protected final boolean tryRelease(int releases) {
    if (!isHeldExclusively())
        throw new IllegalMonitorStateException();
	//1. 同步状态减去写状态
    int nextc = getState() - releases;
	//2. 当前写状态是否为0，为0则释放写锁
    boolean free = exclusiveCount(nextc) == 0;
    if (free)
        setExclusiveOwnerThread(null);
	//3. 不为0则更新同步状态
    setState(nextc);
    return free;
}
```
逻辑与ReentrantLock基本一致，这里需要注意的是，
减少写状态 int nextc = getState() - releases;
只需要用当前同步状态直接减去写状态的原因正是我们刚才所说的**写状态是由同步状态的低16位表示的**。


## 读锁详解
### 读锁的获取
读锁不是独占式锁，即同一时刻该锁可以被多个读线程获取也就是一种**共享式锁**。
而实现写锁的同步语义是通过重写AQS中的tryAcquireShared()方法实现的。
tryAcquireShared()源码如下：
```java
protected final int tryAcquireShared(int unused) {
    Thread current = Thread.currentThread();
    int c = getState();
	//1. 如果写锁已经被获取并且获取写锁的线程不是当前线程的话，当前线程获取读锁失败返回-1
    if (exclusiveCount(c) != 0 &&
        getExclusiveOwnerThread() != current)
        return -1;
    int r = sharedCount(c);
    if (!readerShouldBlock() &&
        r < MAX_COUNT &&
		//2. 当前线程获取读锁
        compareAndSetState(c, c + SHARED_UNIT)) {
		//3. 下面的代码主要是新增的一些功能，比如getReadHoldCount()方法
		//返回当前获取读锁的次数
        if (r == 0) {
            firstReader = current;
            firstReaderHoldCount = 1;
        } else if (firstReader == current) {
            firstReaderHoldCount++;
        } else {
            HoldCounter rh = cachedHoldCounter;
            if (rh == null || rh.tid != getThreadId(current))
                cachedHoldCounter = rh = readHolds.get();
            else if (rh.count == 0)
                readHolds.set(rh);
            rh.count++;
        }
        return 1;
    }
	//4. 处理在第二步中CAS操作失败的自旋,已经实现重入性
    return fullTryAcquireShared(current);
}
```
**当写锁被其他线程获取后，读锁获取失败**，否则获取成功利用CAS更新同步状态。
另外，当前同步状态需要加上SHARED_UNIT（(1 << SHARED_SHIFT)即0x00010000）的原因这是我们在上面所说的同步状态的
高16位用来表示读锁被获取的次数。

### 读锁的释放
读锁释放的实现主要通过方法tryReleaseShared(),源码如下：
```java
@Override
protected final boolean tryReleaseShared(int unused) {
    Thread current = Thread.currentThread();
	// 前面还是为了实现getReadHoldCount等新功能
    if (firstReader == current) {
        // assert firstReaderHoldCount > 0;
        if (firstReaderHoldCount == 1)
            firstReader = null;
        else
            firstReaderHoldCount--;
    } else {
        HoldCounter rh = cachedHoldCounter;
        if (rh == null || rh.tid != getThreadId(current))
            rh = readHolds.get();
        int count = rh.count;
        if (count <= 1) {
            readHolds.remove();
            if (count <= 0)
                throw unmatchedUnlockException();
        }
        --rh.count;
    }
    for (;;) {
        int c = getState();
		// 读锁释放 将同步状态减去读状态即可
        int nextc = c - SHARED_UNIT;
        if (compareAndSetState(c, nextc))
            // Releasing the read lock has no effect on readers,
            // but it may allow waiting writers to proceed if
            // both read and write locks are now free.
            return nextc == 0;
    }
}
```

## 锁降级
读写锁支持锁降级，遵循按照**先获取写锁、获取读锁再释放写锁的次序**，写锁能够降级成为读锁，但不支持锁升级。
ReentrantReadWriteLock中锁降级的应用：
```java
public class CachedData {
    volatile  boolean cacheValid;//是否被缓存
    ReadWriteLock rwl=new ReentrantReadWriteLock();//可重入读写锁  *****位置M*******
    public void processCachedData()//处理缓存的数据
    {
        //注意这里的data是线程私有的，而cacheMap是所有线程公用的
        Object data=null;
        //所以只需要考虑cacheMap的读写锁的问题，而不需要考虑data
        rwl.readLock().lock();//读锁上锁
        if(!this.cacheValid)//在缓存中并不存在     ********位置A********
        {
            //1首先必须释放读锁，因为接下来需要从其他地方读取数据并写入。读写锁是互斥的
            rwl.readLock().unlock();
            //TODO：2写锁上锁
            rwl.writeLock().lock();//位置获取写锁   ********位置B********
            //3再次检测是否在缓存中存在数据，原因是可能存在一个线程在当前线程执行到位置A与位置B之间的时候
            //获取了写锁，并写入数据将cachedValid状态改变
            if(!this.cacheValid){
                data=this.getData();//模拟从数据库或者文件系统等获取数据
                this.cacheValid=true;//将状态更改为缓存系统中已经存在该数据
            }
            //4 在释放写锁之前先进行降级 为了保证使用数据时，数据本身不被改变，需要读锁上锁,然后才释放写锁
            // ********此处设计小知识点：当前线程自己的读锁和写锁并不冲突******
            rwl.readLock().lock();//                       *****位置N*******
            //*********开始持有读锁***********************
            rwl.writeLock().unlock();//释放写锁
        }
        use(data);
        rwl.readLock().unlock();//释放读锁 在位置M、N处，读锁都进行了加锁所以这里需要将锁释放
    }
    public  Integer getData()
    {
        Integer a=new Random().nextInt(1000);
        return a;
    }
    public void use(Object data)//使用数据
    {
        System.out.println(data);
    }
}
```
