<!-- GFM-TOC -->
*  [深入分析wait&notify原理](#深入分析wait_notify原理)
    * []()
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