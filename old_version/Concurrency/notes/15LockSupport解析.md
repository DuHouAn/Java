<!-- GFM-TOC -->
* [十六、LockSupport解析](#LockSupport解析)
  * [LockSupport简介](#LockSupport简介)
  * [LockSupport方法](#LockSupport方法)
<!-- GFM-TOC -->
# LockSupport解析
## LockSupport简介
LockSupport位于java.util.concurrent.locks包下。
LockSupprot是线程的**阻塞原语**，用来**阻塞线程和唤醒线程**。
每个使用LockSupport的线程都会与一个许可关联，
如果该许可可用，并且可在线程中使用，则调用park()将会立即返回，否则可能阻塞。
如果许可尚不可用，则可以调用 unpark 使其可用。
但是注意许可不可重入，也就是说只能调用一次park()方法，否则会一直阻塞。

## LockSupport方法

> **阻塞线程**

```java
void park()//阻塞当前线程，如果调用unpark方法或者当前线程被中断，能从park()方法中返回
void park(Object blocker)//功能同park，入参增加一个Object对象，用来记录导致线程阻塞的阻塞对象，方便进行问题排查；
void parkNanos(long nanos)//阻塞当前线程，最长不超过nanos纳秒，增加了超时返回的特性；
void parkNanos(Object blocker, long nanos)//功能同parkNanos(long nanos)，入参增加一个Object对象，用来记录导致线程阻塞的阻塞对象，方便进行问题排查；
void parkUntil(long deadline)//阻塞当前线程，知道deadline；
void parkUntil(Object blocker, long deadline)//功能同parkUntil(long deadline)，入参增加一个Object对象，用来记录导致线程阻塞的阻塞对象，方便进行问题排查；
```

>  **唤醒线程**

```java
void unpark(Thread thread)//唤醒处于阻塞状态的指定线程
```

实际上LockSupport阻塞和唤醒线程的功能是**依赖于sun.misc.Unsafe**，比如park()方法的功能实现则是靠unsafe.park()方法。
另外在阻塞线程这一系列方法中还有一个很有意思的现象：每个方法都会新增一个带有Object的阻塞对象的重载方法。
那么增加了一个Object对象的入参会有什么不同的地方了？

- 调用park()方法dump线程：

```html
"main" #1 prio=5 os_prio=0 tid=0x02cdcc00 nid=0x2b48 waiting on condition [0x00d6f000]
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:304)
        at learn.LockSupportDemo.main(LockSupportDemo.java:7)
```

- 调用park(Object blocker)方法dump线程:

```html
"main" #1 prio=5 os_prio=0 tid=0x0069cc00 nid=0x6c0 waiting on condition [0x00dcf000]
   java.lang.Thread.State: WAITING (parking)
        at sun.misc.Unsafe.park(Native Method)
        - parking to wait for  <0x048c2d18> (a java.lang.String)
        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
        at learn.LockSupportDemo.main(LockSupportDemo.java:7)
```
通过分别调用这两个方法然后dump线程信息可以看出，
带Object的park方法相较于无参的park方法会增加 **parking to wait for <0x048c2d18>** (a java.lang.String）的信息，
这种信息就类似于记录“案发现场”，有助于工程人员能够迅速发现问题解决问题。

- 注意：

**1.synchronzed致使线程阻塞，线程会进入到BLOCKED状态**

**2.调用LockSupprt方法阻塞线程会致使线程进入到WAITING状态**

```java
public class LockSupportDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "被唤醒");
        });
        thread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(thread);
    }
}
```
thread线程调用LockSupport.park()致使thread阻塞，
当mian线程睡眠3秒结束后通过LockSupport.unpark(thread)方法唤醒thread线程,thread线程被唤醒执行后续操作。
另外，还有一点值得关注的是，LockSupport.unpark(thread)可以**通过指定线程对象唤醒指定的线程**。