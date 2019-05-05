<!-- GFM-TOC -->

* [一、进程与线程](#一进程与线程)
* [二、使用线程](#二使用线程)
* [三、线程状态转换](#三线程状态转换)
* [四、基础线程机制](#四基础线程机制)
* [五、中断](#五中断)
* [六、线程之间的协作](#六线程之间的协作)
* [七、Java 原子操作类](#七Java-原子操作类)
* [八、J.U.C - AQS](#八juc---aqs)
* [九、J.U.C - 其他组件](#九juc---其他组件)
* [十、生产者与消费者](#十生产者与消费者)

<!-- GFM-TOC -->

## 一、进程与线程

### 进程与线程的区别

> **进程与线程的由来**

- 串行：初期的计算机只能串行执行任务，并且需要长时间等待用户输入
- 批处理：预先将用户的指令集中成清单，批量串行处理用户指令，仍然无法并发执行
- 进程：**进程独占内存空间**，保存各自运行状态，相互间**互不干扰**并且可以互相**切换**，为并发处理任务提供了可能
- 线程：共享进程的内存资源，相互间切换更快速，支持更细粒度的任务控制，使进程内的子任务得以并发执行

> **进程和线程的区别**

**进程是资源分配的最小单位，线程是 CPU 调度的最小单位**

- 所有与进程相关的资源，都被记录在 PCB 中

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_1.png)

- 进程是抢占处理机的调度单位；线程属于某个进程，共享其资源
- 线程只由堆栈寄存器、程序计数器和 TCP 组成

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_2.png)

> **总结**

- 线程不能看做独立应用，而进程可看做独立应用
- 进程有独立的地址空间，相互不影响，线程只是进程的不同执行路径
- 线程没有独立的地址空间，多进程程序比多线程程序健壮
- 进程的切换比线程切换开销大

> **多进程与多线程**

- 多进程的意义

CPU 在某个时间点上只能做一件事情，计算机是在进程 1 和 进程 2 间做着频繁切换，且切换速度很快，所以，我们感觉进程 1 和进程 2 在同时进行，其实并不是同时执行的。

**多进程的作用不是提高执行速度，而是提高 CPU 的使用率**。

- 多线程的意义

 多个线程共享同一个进程的资源(堆内存和方法区)，但是栈内存是独立的，一个线程一个栈。所以他们仍然是在抢 CPU 的资源执行。一个时间点上只有能有一个线程执行。而且谁抢到，这个不一定，所以，造成了线程运行的随机性。

**多线程的作用不是提高执行速度，而是为了提高应用程序的使用率**。

### Java 进程与线程的关系

- Java 对操作系统提供的功能进行封装，包括进程和线程
- 运行程序会产生一个进程，进程包含至少一个线程
- 每个进程对应一个 JVM 实例多个线程共享 JVM 里的堆
- Java 采用单线程编程模型，程序会自动创建主线程
- 主线程可以创建子线程，原则上要后与子线程完成执行

## 二、使用线程

- 继承 Thread 类；

- 实现 Runnable 接口；
- 实现 Callable 接口。

实现 Runnable 和 Callable 接口的类只能当做一个可以在线程中运行的任务，不是真正意义上的线程，因此最后还需要通过 Thread 来调用。可以说任务是通过线程驱动从而执行的。

### 继承 Thread 类

需要实现 run() 方法，因为 Thread 类实现了 Runnable 接口。

当调用 start() 方法启动一个线程时，虚拟机会将该线程放入就绪队列中等待被调度，当一个线程被调度时会执行该线程的 run() 方法。

```java
public class MyThread extends Thread{
    private void attack() {
        System.out.println("Fight");
        System.out.println("Current Thread is : " + Thread.currentThread().getName());
    }

    @Override
    public void run() { //重写 run() 方法
        attack();
    }
}
```

> **Java 中 start 和 run 方法的区别**

- 调用 start 方法会创建一个新的子线程并启动
- run 方法只是 Thread 只是 Thread 的一个普通方法

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_3.png)

```java
public class MyThreadTest {
    public static void main(String[] args) {
        Thread t = new MyThread();

        System.out.println("current main thread is : " + Thread.currentThread().getName());
        t.run(); //调用 run() 方法
    }
}
//输出结果：
//current main thread is : main
//Fight
//Current Thread is : main
```

```java
public class MyThreadTest2 {
    public static void main(String[] args) {
        Thread t = new MyThread();

        System.out.println("current main thread is : " + Thread.currentThread().getName());
        t.start(); //调用 run() 方法
    }
}
//输出结果：
//current main thread is : main
//Fight
//Current Thread is : Thread-0
```

### 实现 Runnable 接口

需要实现 run() 方法。

通过 Thread 调用 start() 方法来启动线程。

```java
public class MyRunnable implements Runnable {
    public void run() {
        // ...
    }
}
```

```java
public static void main(String[] args) {
    MyRunnable instance = new MyRunnable();
    Thread thread = new Thread(instance);
    thread.start();
}
```

> **实现接口 VS 继承 Thread**

实现接口会更好一些，因为：

- Thread 是实现了 Runnable 接口的类，使得 run 支持多线程

- Java 不支持多重继承，因此继承了 Thread 类就无法继承其它类，但是可以实现多个接口；
- 类可能只要求可执行就行，继承整个 Thread 类开销过大。

### 实现 Callable 接口

与 Runnable 相比，Callable 可以有返回值，返回值通过 FutureTask 进行封装。

```java
public class MyCallable implements Callable<Integer> {
    public Integer call() {
        return 123;
    }
}
```

```java
public static void main(String[] args) throws ExecutionException, InterruptedException {
    MyCallable mc = new MyCallable();
    FutureTask<Integer> ft = new FutureTask<>(mc);
    Thread thread = new Thread(ft);
    thread.start();
    System.out.println(ft.get());
}
```

> **实现处理线程的返回值**

- **主线程等待法**

```java
public class CycleWait implements Runnable{
    private String value;

    @Override
    public void run() {
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        value = "we have data now";
    }

    public static void main(String[] args) {
        CycleWait cycleWait = new CycleWait();
        Thread t = new Thread(cycleWait);
        t.start();

        System.out.println("value:"+cycleWait.value); 
        //输出结果：
        //value:null
    }
}
```

```java
public static void main(String[] args) {
        CycleWait cycleWait = new CycleWait();
        Thread t = new Thread(cycleWait);
        t.start();

        System.out.println("value:"+cycleWait.value); 
        //输出结果：
        //value:null
	}
```

```java
 public static void main(String[] args) throws InterruptedException {
        CycleWait cycleWait = new CycleWait();
        Thread t = new Thread(cycleWait);
        t.start();

        while (cycleWait.value == null){ //主线程等待法
            Thread.sleep(1000);
        }
        System.out.println("value:"+cycleWait.value);
     	//输出结果：
        //value:we have data now
    }
```

- **使用 Thread 的 join() 阻塞当前线程以等待子线程处理完毕**

```java
public static void main(String[] args) {
        CycleWait cycleWait = new CycleWait();
        Thread t = new Thread(cycleWait);
        t.start();
		t.join();
        System.out.println("value:"+cycleWait.value); 
        //输出结果：
        //value:we have data now
	}
```

- **通过 Callable 接口实现：通过 FutureTask 或者线程池获取**

方式一：通过 FutureTask 获取

```java
public class CycleWait2 implements Callable<String>{
    private String value;
    @Override
    public String call() throws Exception {
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        value = "we have data now";
        return value;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable cycleWait2 = new CycleWait2();
        FutureTask<String> ft = new FutureTask<String>(cycleWait2);
        Thread t = new Thread(ft);
        t.start();
        
        if(!ft.isDone()){
            System.out.println("task has not finished,please wait!");
        }

        System.out.println("value:"+ft.get());
        //输出结果：
        //value:we have data now
    }
}
```

方式二：通过线程池获取

```java
public class CycleWait3 implements Callable<String>{
    private String value;
    @Override
    public String call() throws Exception {
        try {
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        value = "we have data now";
        return value;
    }

    public static void main(String[] args){
        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(new CycleWait3());

        if(!future.isDone()){
            System.out.println("task has not finished,please wait!");
        }

        try {
            System.out.println("value:"+future.get());
             //输出结果：
        	 //value:we have data now
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            service.shutdown(); //关闭线程池
        }
    }
}
```



## 三、线程状态转换 

<div align="center"> 
<img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/96706658-b3f8-4f32-8eb3-dcb7fc8d5381.jpg"/>
</div>

> **新建（New）**

创建后尚未启动。

> **可运行（Runnable）**

可能正在运行，也可能正在等待 CPU 时间片。

包含了操作系统线程状态中的 Running 和 Ready。

> **阻塞（Blocked）**

等待获取一个**排它锁**，如果其线程释放了锁就会结束此状态。

> **无限期等待（Waiting）**

等待其它线程显式地唤醒，否则不会被分配 CPU 时间片。

| 进入方法 | 退出方法 |
| --- | --- |
| 没有设置 Timeout 参数的 Object.wait() 方法 | Object.notify() / Object.notifyAll() |
| 没有设置 Timeout 参数的 Thread.join() 方法 | 被调用的线程执行完毕 |
| LockSupport.park() 方法 | LockSupport.unpark(Thread) |

> **限期等待（Timed Waiting）**

无需等待其它线程显式地唤醒，在一定时间之后会被系统自动唤醒。

调用 Thread.sleep() 方法使线程进入限期等待状态时，常常用“使一个线程睡眠”进行描述。

调用 Object.wait() 方法使线程进入限期等待或者无限期等待时，常常用“挂起一个线程”进行描述。

睡眠和挂起是用来描述行为，而阻塞和等待用来描述状态。

阻塞和等待的区别在于，阻塞是被动的，它是在等待获取一个排它锁。而等待是主动的，通过调用 Thread.sleep() 和 Object.wait() 等方法进入。

| 进入方法 | 退出方法 |
| --- | --- |
| Thread.sleep() 方法 | 时间结束 |
| 设置了 Timeout 参数的 Object.wait() 方法 | 时间结束 / Object.notify() / Object.notifyAll()  |
| 设置了 Timeout 参数的 Thread.join() 方法 | 时间结束 / 被调用的线程执行完毕 |
| LockSupport.parkNanos() 方法 | LockSupport.unpark(Thread) |
| LockSupport.parkUntil() 方法 | LockSupport.unpark(Thread) |

>  **死亡（Terminated）**

可以是线程结束任务之后自己结束，或者产生了异常而结束。


## 四、基础线程机制

### Executor

Executor 管理多个异步任务的执行，而无需程序员显式地管理线程的生命周期。这里的异步是指多个任务的执行互不干扰，不需要进行同步操作。

主要有三种 Executor：

- CachedThreadPool：一个任务创建一个线程；
- FixedThreadPool：所有任务只能使用固定大小的线程；
- SingleThreadExecutor：相当于大小为 1 的 FixedThreadPool。

```java
public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    for (int i = 0; i < 5; i++) {
        executorService.execute(new MyRunnable());
    }
    executorService.shutdown();  //关闭线程池
}
```

### Daemon

守护线程是程序运行时在后台提供服务的线程，不属于程序中不可或缺的部分。

当所有非守护线程结束时，程序也就终止，同时会杀死所有守护线程。

main() 属于非守护线程。

使用 setDaemon() 方法将一个线程设置为守护线程。

```java
public static void main(String[] args) {
    Thread thread = new Thread(new MyRunnable());
    thread.setDaemon(true);
}
```

### sleep()

Thread.sleep(millisec) 方法会休眠当前正在执行的线程，millisec 单位为毫秒。

sleep() 可能会抛出 InterruptedException，因为异常不能跨线程传播回 main() 中，因此必须在本地进行处理。线程中抛出的其它异常也同样需要在本地进行处理。

```java
public void run() {
    try {
        Thread.sleep(3000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

### yield()

对静态方法 Thread.yield() 的调用声明了当前线程已经完成了生命周期中最重要的部分，可以切换给其它线程来执行。该方法**只是对线程调度器的一个建议**，而且也只是建议具有相同优先级的其它线程可以运行。

```java
public class YieldDemo {
    public static void main(String[] args) {
        Runnable yieldTask = new Runnable() {
            @Override
            public void run() {
                for(int i=1;i<=10;i++){
                    System.out.println(Thread.currentThread().getName()+"\t"+i);
                    if(i==5){
                        Thread.yield(); //只是对线程调度器的一个建议
                    }
                }
            }
        };

        Thread t1 = new Thread(yieldTask,"Thread-A");
        Thread t2 = new Thread(yieldTask,"Thread-B");

        t1.start();
        t2.start();
    }
}
```

```html
Thread-A	1
Thread-A	2
Thread-A	3
Thread-A	4
Thread-A	5
Thread-B	1
Thread-B	2
Thread-B	3
Thread-B	4
Thread-A	6
Thread-B	5
Thread-A	7
Thread-B	6
Thread-A	8
Thread-B	7
Thread-A	9
Thread-B	8
Thread-B	9
Thread-A	10
Thread-B	10
```

## 五、中断

一个线程执行完毕之后会自动结束，如果在运行过程中发生异常也会提前结束。

###  interrupt() 

通过调用一个线程的 interrupt() 来中断该线程：

- 如果该线程处于阻塞、限期等待或者无限期等待状态，那么就会抛出 InterruptedException，从而提前结束该线程。但是**不能中断 I/O 阻塞和 synchronized 锁阻塞**。
- 如果该线程处于正常活动状态，那么会将该线程的中断标志设置为 true。被设置中断标志的线程将继续正常运行，不受影响。

对于以下代码。在 main() 中启动一个线程之后再中断它，由于线程中调用了 Thread.sleep() 方法，因此会抛出一个 InterruptedException，从而提前结束线程，不执行之后的语句。

```java
public class InterruptExample1 {
    private static class MyThread1 extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(2000); //thread1 进入限期等待状态
                System.out.println("Thread run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread run");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new MyThread1();
        thread1.start();
        thread1.interrupt(); //中断 thread1
        System.out.println("Main run");
    }
}
```

```java
Main run
java.lang.InterruptedException: sleep interrupted
    at java.lang.Thread.sleep(Native Method)
    at InterruptExample.lambda$main$0(InterruptExample.java:5)
    at InterruptExample$$Lambda$1/713338599.run(Unknown Source)
    at java.lang.Thread.run(Thread.java:745)
```

```java
public class InterruptExample2 {
    private static class MyThread1 extends Thread {
        @Override
        public void run() { //thread1 线程处于正常活动状态
            System.out.println("Thread run");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new MyThread1();
        thread1.start();
        thread1.interrupt(); //中断 thread1
        System.out.println("Main run");
    }
}
```

```html
Main run
Thread run
```

### interrupted()

如果一个线程的 run() 方法执行一个无限循环，并且没有执行 sleep() 等会抛出 InterruptedException 的操作，那么调用线程的 interrupt() 方法就无法使线程提前结束。

但是调用 interrupt() 方法会设置线程的中断标记，此时**调用 interrupted() 方法会返回 true**。因此可以在循环体中使用 interrupted() 方法来判断线程是否处于中断状态，从而提前结束线程。

```java
public class InterruptExample {
    public static void main(String[] args) throws InterruptedException {
        Runnable interruptTask = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                try {
                    //在正常运行任务时，经常检查本线程的中断标志位，如果被设置了中断标志就自行停止线程
                    while (!Thread.currentThread().isInterrupted()) {
                        Thread.sleep(100); // 休眠100ms
                        i++;
                        System.out.println(Thread.currentThread().getName() + " (" + Thread.currentThread().getState() + ") loop " + i);
                    }
                } catch (InterruptedException e) {
                    //在调用阻塞方法时正确处理InterruptedException异常。（例如，catch异常后就结束线程。）
                    System.out.println(Thread.currentThread().getName() + " (" + Thread.currentThread().getState() + ") catch InterruptedException.");
                }
            }
        };
        Thread t1 = new Thread(interruptTask, "t1");
        System.out.println(t1.getName() +" ("+t1.getState()+") is new.");

        t1.start();                      // 启动“线程t1”
        System.out.println(t1.getName() +" ("+t1.getState()+") is started.");

        // 主线程休眠300ms，然后主线程给t1发“中断”指令。
        Thread.sleep(300);
        t1.interrupt();
        System.out.println(t1.getName() +" ("+t1.getState()+") is interrupted.");

        // 主线程休眠300ms，然后查看t1的状态。
        Thread.sleep(300);
        System.out.println(t1.getName() +" ("+t1.getState()+") is interrupted now.");
    }
}
```

```java
t1 (NEW) is new.
t1 (RUNNABLE) is started.
t1 (RUNNABLE) loop 1
t1 (RUNNABLE) loop 2
t1 (TIMED_WAITING) is interrupted.
t1 (RUNNABLE) catch InterruptedException.
t1 (TERMINATED) is interrupted now.
```

### Executor 的中断操作

调用 Executor 的 shutdown() 方法会等待线程都执行完毕之后再关闭，但是如果调用的是 shutdownNow() 方法，则相当于调用每个线程的 interrupt() 方法。

以下使用 Lambda 创建线程，相当于创建了一个匿名内部线程。

```java
public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> {
        try {
            Thread.sleep(2000);
            System.out.println("Thread run");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });
    executorService.shutdownNow();
    System.out.println("Main run");
}
```

```java
Main run
java.lang.InterruptedException: sleep interrupted
    at java.lang.Thread.sleep(Native Method)
    at ExecutorInterruptExample.lambda$main$0(ExecutorInterruptExample.java:9)
    at ExecutorInterruptExample$$Lambda$1/1160460865.run(Unknown Source)
    at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
    at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
    at java.lang.Thread.run(Thread.java:745)
```

如果只想中断 Executor 中的一个线程，可以通过使用 submit() 方法来提交一个线程，它会返回一个 Future<?> 对象，通过调用该对象的 cancel(true) 方法就可以中断线程。

```java
Future<?> future = executorService.submit(() -> {
    // ..
});
future.cancel(true);
```

## 六、线程之间的协作

当多个线程可以一起工作去解决某个问题时，如果某些部分必须在其它部分之前完成，那么就需要对线程进行协调。

### join()

在线程中调用另一个线程的 join() 方法，会将当前线程挂起，而不是忙等待，直到目标线程结束。

对于以下代码，虽然 b 线程先启动，但是因为在 b 线程中调用了 a 线程的 join() 方法，b 线程会等待 a 线程结束才继续执行，因此最后能够保证 a 线程的输出先于 b 线程的输出。

```java
public class JoinExample {

    private class A extends Thread {
        @Override
        public void run() {
            System.out.println("A");
        }
    }

    private class B extends Thread {

        private A a;

        B(A a) {
            this.a = a;
        }

        @Override
        public void run() {
            try {
                a.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B");
        }
    }

    public void test() {
        A a = new A();
        B b = new B(a);
        b.start();
        a.start();
    }
}
```

```java
public static void main(String[] args) {
    JoinExample example = new JoinExample();
    example.test();
}
```

```java
A
B
```

### wait() notify() notifyAll()

调用 wait() 使得线程等待某个条件满足，线程在等待时会被挂起，当其他线程的运行使得这个条件满足时，其它线程会调用 notify() 或者 notifyAll() 来唤醒挂起的线程。

它们都属于 Object 的一部分，而不属于 Thread。

只能用在同步方法或者同步控制块中使用，否则会在运行时抛出 IllegalMonitorStateException。

使用 wait() 挂起期间，线程会释放锁。这是因为，如果没有释放锁，那么其它线程就无法进入对象的同步方法或者同步控制块中，那么就无法执行 notify() 或者 notifyAll() 来唤醒挂起的线程，造成死锁。

```java
public class WaitNotifyExample {

    public synchronized void before() {
        System.out.println("before");
        notifyAll();
    }

    public synchronized void after() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after");
    }
}
```

```java
public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    WaitNotifyExample example = new WaitNotifyExample();
    executorService.execute(() -> example.after());
    executorService.execute(() -> example.before());
}
```

```java
before
after
```

> **wait() 和 sleep() 的区别**

基本的差别：

- wait() 是 Object 的方法，而 sleep() 是 Thread 的静态方法；
- sleep() 方法可以在任何地方使用，而 wait() 方法只能在 synchronized 块或者同步方法中使用；

本质区别：

- Thread.sleep 只会让出 CPU，不会导致锁行为的变化
- Object.wait 不仅让出 CPU ，还会释放已经占有的同步资源锁

```java
public class WaitSleepDemo {
    public static void main(String[] args) {
        final Object lock = new Object();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread A is waiting to get lock");
                synchronized (lock){
                    try {
                        System.out.println("thread A get lock");
                        Thread.sleep(20); 
                        //Thread.sleep 只会让出 CPU，不会导致锁行为的变化
                        System.out.println("thread A do wait method");
                        
                        //==========位置 A ==========
                        //lock.wait(1000); 
                        //Object.wait 不仅让出 CPU ，还会释放已经占有的同步资源锁
                        
                        Thread.sleep(1000); 
                        //Thread.sleep 只会让出 CPU，不会导致锁行为的变化
                        //===========================
                        
                        System.out.println("thread A is done");
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        t1.start();

        try{
            Thread.sleep(10); //主线程等待 10 ms
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread B is waiting to get lock");
                synchronized (lock){
                    try {
                        System.out.println("thread B get lock");
                        System.out.println("thread B is sleeping 10 ms");
                        Thread.sleep(10);
                        //Thread.sleep 只会让出 CPU，不会导致锁行为的变化
                        System.out.println("thread B is done");
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
```

位置 A 为  `lock.wait(1000);` 输出结果： 

```html
thread A is waiting to get lock
thread A get lock
thread A do wait method
thread B is waiting to get lock
thread B get lock
thread B is sleeping 10 ms
thread B is done
thread A is done
```

位置 A 为  `Thread.sleep(1000);` 输出结果： 

```html
thread A is waiting to get lock
thread A get lock
thread B is waiting to get lock
thread A do wait method
thread A is done
thread B get lock
thread B is sleeping 10 ms
thread B is done
```

> **notify() 和 notifyAll() 的区别**

两个重要概念：

- 锁池（EntryList）

假设线程 A 已经拥有了**某个对象的锁**，而其他线程 B、C 想要调用这个对象的同步方法（或者同步代码块），由于 B 、C 线程在进入对象的同步方法（或者同步代码块）之前必须先获得该对象锁的拥有权，而恰巧该对象的锁目前正在被线程 A 所占用，此时 B 、C 线程就会被阻塞，进入一个地方去等待锁的释放，这个地方便是该对象的锁池。

- 等待池（WaitSet）

假设线程 A 调用了某个对象的 wait() 方法，线程 A 就会释放该对象的锁，同时线程 A 就进入该对象的等待池，**进入到等待池的线程不会竞争该对象的锁**。

区别：

notifyAll() 会让所有处于**等待池的线程全部进入锁池**去竞争锁

```java
public class NotificationDemo {
    private volatile boolean go = false;

    public static void main(String args[]) throws InterruptedException {
        final NotificationDemo test = new NotificationDemo();

        Runnable waitTask = new Runnable(){
            @Override
            public void run(){
                try {
                    test.shouldGo();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " finished Execution");
            }
        };

        Runnable notifyTask = new Runnable(){
            @Override
            public void run(){
                test.go();
                System.out.println(Thread.currentThread().getName() + " finished Execution");
            }
        };

        //t1、t2、t3 等待
        Thread t1 = new Thread(waitTask, "WT1");
        Thread t2 = new Thread(waitTask, "WT2");
        Thread t3 = new Thread(waitTask, "WT3");
        //t4 唤醒
        Thread t4 = new Thread(notifyTask,"NT1");
        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(200);
        t4.start();
    }

    //wait and notify can only be called from synchronized method or block
    private synchronized void shouldGo() throws InterruptedException {
        while(go != true){
            System.out.println(Thread.currentThread()
                    + " is going to wait on this object");
            wait(); //this.wait 不仅让出 CPU ，还会释放已经占有的同步资源锁
            System.out.println(Thread.currentThread() + " is woken up");
        }
        go = false; //resetting condition
    }

    // both shouldGo() and go() are locked on current object referenced by "this" keyword
    private synchronized void go() {
        while (go == false){
            System.out.println(Thread.currentThread()
                    + " is going to notify all or one thread waiting on this object");

            go = true; //making condition true for waiting thread
            //===================位置 A =====================
            //notify(); //只会唤醒 WT1, WT2,WT3 中的一个线程
            notifyAll(); //所有的等待线程 WT1, WT2,WT3 都会被唤醒
            //==============================================
        }
    }
}
```

位置 A 为  `notify();` 输出结果： 

```html
Thread[WT1,5,main] is going to wait on this object
Thread[WT2,5,main] is going to wait on this object
Thread[WT3,5,main] is going to wait on this object
Thread[NT1,5,main] is going to notify all or one thread waiting on this object
Thread[WT1,5,main] is woken up
WT1 finished Execution
NT1 finished Execution
```

位置 B 为  `notifyAll();` 输出结果： 

```html
Thread[WT1,5,main] is going to wait on this object
Thread[WT3,5,main] is going to wait on this object
Thread[WT2,5,main] is going to wait on this object
Thread[NT1,5,main] is going to notify all or one thread waiting on this object
Thread[WT2,5,main] is woken up
NT1 finished Execution
WT2 finished Execution
Thread[WT3,5,main] is woken up
Thread[WT3,5,main] is going to wait on this object
Thread[WT1,5,main] is woken up
Thread[WT1,5,main] is going to wait on this object
```

### await() signal() signalAll()

java.util.concurrent 类库中提供了 Condition 类来实现线程之间的协调，可以在 Condition 上调用 await() 方法使线程等待，其它线程调用 signal() 或 signalAll() 方法唤醒等待的线程。

相比于 wait() 这种等待方式，await() 可以指定等待的条件，因此更加灵活。

使用 Lock 来获取一个 Condition 对象。

```java
public class AwaitSignalExample {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void before() {
        lock.lock();
        try {
            System.out.println("before");
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void after() {
        lock.lock();
        try {
            condition.await();
            System.out.println("after");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
```

```java
public static void main(String[] args) {
    ExecutorService executorService = Executors.newCachedThreadPool();
    AwaitSignalExample example = new AwaitSignalExample();
    executorService.execute(() -> example.after());
    executorService.execute(() -> example.before());
}
```

```java
before
after
```

## 七、Java 原子操作类

由于 synchronized 是采用的是**悲观锁策略**，并不是特别高效的一种解决方案。 实际上，在 J.U.C下 的 atomic 包提供了一系列的操作简单，性能高效，并能保证线程安全的类，去更新基本类型变量、数组元素、引用类型以及更新对象中的字段类型。 atomic 包下的这些类都是采用的是**乐观锁策略**去原子更新数据，在 Java 中则是**使用 CAS操作具体实现**。

### 1、原子更新基本类

使用原子的方式更新基本类型，atomic 包提供了以下 3 个类：

|      类       |         说明          |
| :-----------: | :-------------------: |
| AtomicBoolean | 原子更新 boolean 类型 |
| AtomicInteger |     原子更新整型      |
|  AtomicLong   |    原子更新长整型     |

以 AtomicInteger 为例总结常用的方法:

|          方法           |                             说明                             |
| :---------------------: | :----------------------------------------------------------: |
|  addAndGet(int delta)   | 以原子方式将输入的数值与实例中原本的值相加，并返回最后的结果 |
|    incrementAndGet()    | 以原子的方式将实例中的原值进行加1操作，并返回最终相加后的结果 |
| getAndSet(int newValue) |              将实例中的值更新为新值，并返回旧值              |
|    getAndIncrement()    |     以原子的方式将实例中的原值 +1，返回的是自增前的旧值      |

> **AtomicInteger **

 AtomicInteger 测试：

```java
public class AtomicIntegerDemo {
    // 请求总数
    public static int clientTotal = 5000;

    // 同时并发执行的线程数
    public static int threadTotal = 200;

    //java.util.concurrent.atomic.AtomicInteger;
    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //Semaphore和CountDownLatch模拟并发
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal ; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("count:{"+count.get()+"}");
    }

    public static void add() {
        count.incrementAndGet();
    }
}
```

```html
count:{5000}
```

 AtomicInteger 的 getAndIncrement() 方法源码：

```java
private static final Unsafe unsafe = Unsafe.getUnsafe();

public final int incrementAndGet() {
        return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
}
```

实际上是调用了 unsafe 实例的 getAndAddInt 方法。Unsafe 类在 sun.misc 包下，Unsafe 类提供了一些底层操作，atomic 包下的原子操作类的也主要是通过 Unsafe 类提供的 compareAndSwapObject、compareAndSwapInt 和 compareAndSwapLong  等一系列提供 CAS 操作的方法来进行实现。

> **AtomicLong**

AtomicLong 的实现原理和 AtomicInteger 一致，只不过一个针对的是 long 变量，一个针对的是 int 变量。

> **AtomicBoolean**

AtomicBoolean 的核心方法是 compareAndSet() 方法：

```java
public final boolean compareAndSet(boolean expect, boolean update) {
    int e = expect ? 1 : 0;
    int u = update ? 1 : 0;
    return unsafe.compareAndSwapInt(this, valueOffset, e, u);
}
```

可以看出，compareAndSet 方法的实际上也是先转换成 0、1 的整型变量，然后是通过针对 int 型变量的原子更新方法 compareAndSwapInt 来实现的。atomic 包中只提供了对 boolean ，int ，long 这三种基本类型的原子更新的方法，参考对 boolean 更新的方式，原子更新 char，double，float 也可以采用类似的思路进行实现。

### 2、原子更新数组

通过原子的方式更新数组里的某个元素，atomic 包提供了以下 3 个类：

|          类          |             说明             |
| :------------------: | :--------------------------: |
|  AtomicIntegerArray  |   原子更新整型数组中的元素   |
|   AtomicLongArray    |  原子更新长整型数组中的元素  |
| AtomicReferenceArray | 原子更新引用类型数组中的元素 |

这几个类的用法一致，就以AtomicIntegerArray来总结下常用的方法：

|                     方法                     |                       说明                        |
| :------------------------------------------: | :-----------------------------------------------: |
|         addAndGet(int i, int delta)          | 以原子更新的方式将数组中索引为i的元素与输入值相加 |
|            getAndIncrement(int i)            |   以原子更新的方式将数组中索引为i的元素自增 +1    |
| compareAndSet(int i, int expect, int update) |       将数组中索引为 i 的位置的元素进行更新       |

AtomicIntegerArray 与 AtomicInteger 的方法基本一致，只不过在 AtomicIntegerArray 的方法中会多一个指定数组索引位 i。

```java
public class AtomicIntegerArrayDemo {
    //    private static AtomicInteger atomicInteger = new AtomicInteger(1);
    private static int[] value = new int[]{1, 2, 3};
    private static AtomicIntegerArray integerArray = new AtomicIntegerArray(value);

    public static void main(String[] args) {
        //对数组中索引为1的位置的元素加5
        int result = integerArray.getAndAdd(1, 5);
        System.out.println(integerArray.get(1));
        System.out.println(result);
    }
}
```

```html
7
2
```

getAndAdd 方法将位置为 1 的元素 +5，从结果可以看出索引为 1 的元素变成了 7 ，该方法返回的也是相加之前的数为 2。

### 3、原子更新引用类型

如果需要原子更新引用类型变量的话，为了保证线程安全，atomic 也提供了相关的类：

|             类              |             说明             |
| :-------------------------: | :--------------------------: |
|       AtomicReference       |       原子更新引用类型       |
| AtomicReferenceFieldUpdater |   原子更新引用类型里的字段   |
|   AtomicMarkableReference   | 原子更新带有标记位的引用类型 |

这几个类的使用方法也是基本一样的，以AtomicReference为例，来说明这些类的基本用法：

```java
public class AtomicDemo {

    private static AtomicReference<User> reference = new AtomicReference<>();

    public static void main(String[] args) {
        User user1 = new User("a",1);
        reference.set(user1);
        User user2 = new User("b",2);
        User user = reference.getAndSet(user2);
        System.out.println(user);
        System.out.println(reference.get());
    }

    static class User {
        private String userName;
        private int age;

        public User(String userName, int age) {
            this.userName = userName;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "userName='" + userName + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
```

```html
User{userName='a', age=1}
User{userName='b', age=2}
```

首先构建一个 user1 对象，然会把 user1 对象设置进 AtomicReference  中，然后调用 getAndSet 方法。从结果可以看出，该方法会原子更新引用的 user 对象，变为`User{userName='b', age=2}`，返回的是原来的 user 对象User`{userName='a', age=1}`。

### 4、原子更新字段类型

如果需要更新对象的某个字段，并在多线程的情况下，能够保证线程安全，atomic 同样也提供了相应的原子操作类：

|            类            |                             说明                             |
| :----------------------: | :----------------------------------------------------------: |
| AtomicIntegeFieldUpdater |                      原子更新整型字段类                      |
|  AtomicLongFieldUpdater  |                     原子更新长整型字段类                     |
|  AtomicStampedReference  | 原子更新带版本号引用类型。<br/>为什么在更新的时候会带有版本号，是为了解决 CAS 的 ABA 问题 |

要想使用原子更新字段需要两步操作：

- 原子更新字段类都是**抽象类**，只能通过静态方法 newUpdater 来创建一个更新器，并且需要设置想要更新的类和属性
- 更新类的属性必须使用 public volatile 进行修饰

```java
public class AtomicIntegerFieldUpdaterDemo {
    private static AtomicIntegerFieldUpdater updater =
            AtomicIntegerFieldUpdater.newUpdater(User.class,"age");

    public static void main(String[] args) {
        User user = new User("a", 1);
        System.out.println(user);
        int oldValue = updater.getAndAdd(user, 5);
        System.out.println(user);
    }

    static class User {
        private String userName;
        public volatile int age;

        public User(String userName, int age) {
            this.userName = userName;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "userName='" + userName + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
```

```html
User{userName='a', age=1}
User{userName='a', age=6}
```

创建 AtomicIntegerFieldUpdater 是通过它提供的静态方法进行创建， getAndAdd 方法会将指定的字段加上输入的值，并且返回相加之前的值。 user 对象中 age 字段原值为 1，+5 之后，可以看出 user 对象中的 age 字段的值已经变成了 6。

## 八、J.U.C - AQS

java.util.concurrent（J.U.C）大大提高了并发性能，AQS 被认为是 J.U.C 的核心。

### CountDownLatch

用来控制一个线程等待多个线程。

维护了一个计数器 cnt，每次调用 countDown() 方法会让计数器的值减 1，减到 0 的时候，那些因为调用 await() 方法而在等待的线程就会被唤醒。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/912a7886-fb1d-4a05-902d-ab17ea17007f.jpg"/> </div>



> **场景1：程序执行需要等待某个条件完成后，才能进行后面的操作**

比如父任务等待所有子任务都完成的时候， 再继续往下进行。

```java
public class CountDownLatchExample {
    //线程数量
    private static int threadCount=10;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService= Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch=new CountDownLatch(threadCount);


        for (int i = 1; i <= threadCount; i++) {
            final int threadNum=i;
            executorService.execute(()->{
                try {
                    test(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }

            });
        }
        countDownLatch.await();
        //上面的所有线程都执行完了,再执行主线程
        System.out.println("Finished!");
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        Thread.sleep(100);
        System.out.println("run: Thread "+threadNum);
        Thread.sleep(100);
    }
}
```

```html
run: Thread 8
run: Thread 7
run: Thread 6
run: Thread 5
run: Thread 4
run: Thread 3
run: Thread 2
run: Thread 1
run: Thread 10
run: Thread 9
Finished!
```



> **场景2：指定执行时间的情况，超过这个任务就不继续等待了，完成多少算多少。**

```java
public class CountDownLatchExample2 {
    //线程数量
    private static int threadCount=10;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService= Executors.newCachedThreadPool();

        final CountDownLatch countDownLatch=new CountDownLatch(threadCount);


        for (int i = 1; i <= threadCount; i++) {
            final int threadNum=i;
            executorService.execute(()->{
                try {
                    test(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }

            });
        }
        countDownLatch.await(10, TimeUnit.MILLISECONDS);
        //上面线程如果在10 毫秒内未完成，则有可能会执行主线程
        System.out.println("Finished!");
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        Thread.sleep(10);
        System.out.println("run: Thread "+threadNum);
    }
}
```

```html
run: Thread 10
run: Thread 4
run: Thread 3
run: Thread 2
Finished!
run: Thread 9
run: Thread 5
run: Thread 8
run: Thread 6
run: Thread 7
run: Thread 1
```

### CyclicBarrier

用来控制多个线程互相等待，只有当多个线程都到达时，这些线程才会继续执行。它要做的事情是，让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被拦截的线程才会继续执行。

和 CountdownLatch 相似，都是通过维护计数器来实现的。线程执行 await() 方法之后计数器会减 1，并进行等待，直到计数器为 0，所有调用 await() 方法而在等待的线程才能继续执行。

CyclicBarrier 和 CountdownLatch 的一个区别是，CyclicBarrier 的计数器通过调用 reset() 方法可以循环使用，所以它才叫做循环屏障。

CyclicBarrier 有两个构造函数，其中 parties 指示计数器的初始值，barrierAction 在所有线程都到达屏障的时候会执行一次。

```java
public CyclicBarrier(int parties, Runnable barrierAction) {
    if (parties <= 0) throw new IllegalArgumentException();
    this.parties = parties;
    this.count = parties;
    this.barrierCommand = barrierAction;
}

public CyclicBarrier(int parties) {
    this(parties, null);
}
```

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/f944fac3-482b-4ca3-9447-17aec4a3cca0.png"/> </div>

```java
public class CyclicBarrierExample {
    //指定必须有6个运动员到达才行
    private static CyclicBarrier barrier = new CyclicBarrier(6, () -> {
        System.out.println("所有运动员入场，裁判员一声令下！！！！！");
    });
    public static void main(String[] args) {
        System.out.println("运动员准备进场，全场欢呼............");

        ExecutorService service = Executors.newFixedThreadPool(6);
        for (int i = 0; i < 6; i++) {
            service.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 运动员，进场");
                    barrier.await();
                    System.out.println(Thread.currentThread().getName() + "  运动员出发");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }

        service.shutdown();
    }
}
```

```html
运动员准备进场，全场欢呼............
pool-1-thread-1 运动员，进场
pool-1-thread-2 运动员，进场
pool-1-thread-3 运动员，进场
pool-1-thread-4 运动员，进场
pool-1-thread-5 运动员，进场
pool-1-thread-6 运动员，进场
所有运动员入场，裁判员一声令下！！！！！
pool-1-thread-6  运动员出发
pool-1-thread-1  运动员出发
pool-1-thread-4  运动员出发
pool-1-thread-3  运动员出发
pool-1-thread-2  运动员出发
pool-1-thread-5  运动员出发
```

> **场景：多线程计算数据，最后合并计算结果。**

```java
public class CyclicBarrierExample2 {
    private static int threadCount = 10;

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(5);

        ExecutorService executorService= Executors.newCachedThreadPool();


        for (int i = 0; i < threadCount; i++) {
            final int threadNum=i;
            executorService.execute(()->{
                try {
                    System.out.println("before..."+threadNum);
                    cyclicBarrier.await();
                    System.out.println("after..."+threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
```

```html
before...1
before...4
before...3
before...2
before...6
before...5
before...7
before...8
after...6
before...9
after...1
after...2
before...10
after...3
after...7
after...8
after...4
after...9
after...5
after...10
```

> **CountDownLatch 与 CyclicBarrier 比较**

- CountDownLatch 一般用于某个线程 A 等待若干个其他线程执行完后再执行。CountDownLatch 强调一个线程等多个线程完成某件事情；

  CyclicBarrier 一般用于一组线程互相等待至某个状态，然后这一组线程再同时执行。CyclicBarrier是多个线程互等，等大家都完成，再携手共进。

- 调用 CountDownLatch 的 countDown 方法后，当前线程并不会阻塞，会继续往下执行；

  调用 CyclicBarrier 的 await 方法，会阻塞当前线程，直到 CyclicBarrier 指定的线程全部都到达了指定点时，才能继续往下执行。

- CountDownLatch 方法比较少，操作比较简单；

  CyclicBarrier 提供的方法更多，比如能够通过 getNumberWaiting()，isBroken()  等方法获取当前多个线程的状态，并且 **CyclicBarrier 的构造方法可以传入 barrierAction**，指定当所有线程都到达时执行的业务功能。

- CountDownLatch 是不能复用的；

  CyclicLatch 是可以复用的。



### Semaphore

Semaphore 类似于操作系统中的信号量，可以控制对互斥资源的访问线程数。

其中`acquire()`方法，用来获取资源，`release()`方法用来释放资源。Semaphore维护了当前访问的个数，通过提供**同步机制**来控制同时访问的个数。

> **场景：特殊资源的并发访问控制**

比如数据库的连接数最大只有 20，而上层的并发数远远大于 20，这时候如果不作限制， 可能会由于无法获取连接而导致并发异常，这时候可以使用 Semaphore 来进行控制。当信号量设置为1的时候，就和单线程很相似了。

```java
//每次获取一个许可
public class SemaphoreExample {
    private static int clientCount = 3;
    private static int totalRequestCount = 10;

    public static void main(String[] args) {
        ExecutorService executorService= Executors.newCachedThreadPool();

        //Semaphore允许的最大许可数为 clientCount ，
        //也就是允许的最大并发执行的线程个数为 clientCount
        final Semaphore semaphore=new Semaphore(clientCount);

        for(int i=1;i<=totalRequestCount;i++){
            final int threadNum=i;
            executorService.execute(() -> {
                try{
                    semaphore.acquire();//每次获取一个许可
                    test(threadNum);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release(); //释放一个许可
                }
            });
        }
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        System.out.println("run: "+threadNum);
        Thread.sleep(1000); // 线程睡眠 1 s
    }
}
```

```html
run: 1
run: 2
run: 3
//---- 1 s -----
run: 4
run: 5
run: 6
//---- 1 s -----
run: 7
run: 8
run: 9
//---- 1 s -----
run: 10
```



```java
//每次获取多个许可
public class SemaphoreExample2 {
    private static int clientCount = 3;
    private static int totalRequestCount = 10;

    public static void main(String[] args) {
        ExecutorService executorService= Executors.newCachedThreadPool();

        final Semaphore semaphore=new Semaphore(clientCount);

        for(int i=1;i<=totalRequestCount;i++){
            final int threadNum=i;
            executorService.execute(() -> {
                try{
                    semaphore.acquire(3);//每次获取3个许可
                    //并发数是 3 ,一次性获取 3 个许可，同 1s 内无其他许可释放，相当于单线程
                    test(threadNum);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release(3); //释放 3 个许可
                }
            });
        }
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        System.out.println("run: "+threadNum);
        Thread.sleep(1000); // 线程睡眠 1 s
    }
}
```

```html
run: 1
//---- 1 s -----
run: 2
//---- 1 s -----
run: 3
//---- 1 s -----
run: 4
//---- 1 s -----
run: 5
//---- 1 s -----
run: 6
//---- 1 s -----
run: 7
//---- 1 s -----
run: 8
//---- 1 s -----
run: 10
//---- 1 s -----
run: 9
```



```java
public class SemaphoreExample3 {
    private static int clientCount = 3;
    private static int totalRequestCount = 10;

    public static void main(String[] args) {
        ExecutorService executorService= Executors.newCachedThreadPool();

        final Semaphore semaphore=new Semaphore(clientCount);

        for(int i=1;i<=totalRequestCount;i++){
            final int threadNum=i;
            executorService.execute(() -> {
                try{
                    if(semaphore.tryAcquire()){
                        //尝试获取一个许可
                        test(threadNum);
                        semaphore.release();
                    }
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        System.out.println("run: "+threadNum);
        Thread.sleep(1000); // 线程睡眠 1 s
    }
}
```

```html
run: 1
run: 2
run: 3
```

## 九、J.U.C - 其它组件

### FutureTask

在介绍 Callable 时我们知道它可以有返回值，返回值通过 Future<V> 进行封装。FutureTask 实现了 RunnableFuture 接口，该接口继承自 Runnable 和 Future<V> 接口，这使得 FutureTask 既可以当做一个任务执行，也可以有返回值。

```java
public class FutureTask<V> implements RunnableFuture<V>
```

```java
public interface RunnableFuture<V> extends Runnable, Future<V>
```

FutureTask 可用于异步获取执行结果或取消执行任务的场景。当一个计算任务需要执行很长时间，那么就可以用 FutureTask 来封装这个任务，主线程在完成自己的任务之后再去获取结果。

```java
public class FutureTaskExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int result = 0;
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(10);
                    result += i;
                }
                return result;
            }
        });

        Thread computeThread = new Thread(futureTask);
        computeThread.start();

        Thread otherThread = new Thread(() -> {
            System.out.println("other task is running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        otherThread.start();
        System.out.println(futureTask.get());
    }
}
```

```java
other task is running...
4950
```

### BlockingQueue

java.util.concurrent.BlockingQueue 接口有以下阻塞队列的实现：

-  **FIFO 队列** ：LinkedBlockingQueue、ArrayBlockingQueue（固定长度）
-  **优先级队列** ：PriorityBlockingQueue

提供了阻塞的 take() 和 put() 方法：如果队列为空 take() 将阻塞，直到队列中有内容；如果队列为满 put() 将阻塞，直到队列有空闲位置。

**使用 BlockingQueue 实现生产者消费者问题** 

```java
public class ProducerConsumer {

    private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

    private static class Producer extends Thread {
        @Override
        public void run() {
            try {
                queue.put("product");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("produce..");
        }
    }

    private static class Consumer extends Thread {

        @Override
        public void run() {
            try {
                String product = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("consume..");
        }
    }
}
```

```java
public static void main(String[] args) {
    for (int i = 0; i < 2; i++) {
        Producer producer = new Producer();
        producer.start();
    }
    for (int i = 0; i < 5; i++) {
        Consumer consumer = new Consumer();
        consumer.start();
    }
    for (int i = 0; i < 3; i++) {
        Producer producer = new Producer();
        producer.start();
    }
}
```

```html
produce..produce..consume..consume..produce..consume..produce..consume..produce..consume..
```

### ForkJoin

主要用于并行计算中，和 MapReduce 原理类似，都是把大的计算任务拆分成多个小任务并行计算。

```java
public class ForkJoinExample extends RecursiveTask<Integer> {

    private final int threshold = 5;
    private int first;
    private int last;

    public ForkJoinExample(int first, int last) {
        this.first = first;
        this.last = last;
    }

    @Override
    protected Integer compute() {
        int result = 0;
        if (last - first <= threshold) {
            // 任务足够小则直接计算
            for (int i = first; i <= last; i++) {
                result += i;
            }
        } else {
            // 拆分成小任务
            int middle = first + (last - first) / 2;
            ForkJoinExample leftTask = new ForkJoinExample(first, middle);
            ForkJoinExample rightTask = new ForkJoinExample(middle + 1, last);
            leftTask.fork();
            rightTask.fork();
            result = leftTask.join() + rightTask.join();
        }
        return result;
    }
}
```

```java
public static void main(String[] args) throws ExecutionException, InterruptedException {
    ForkJoinExample example = new ForkJoinExample(1, 10000);
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    Future result = forkJoinPool.submit(example);
    System.out.println(result.get());
}
```

ForkJoin 使用 ForkJoinPool 来启动，它是一个特殊的线程池，线程数量取决于 CPU 核数。

```java
public class ForkJoinPool extends AbstractExecutorService
```

ForkJoinPool 实现了工作窃取算法来提高 CPU 的利用率。每个线程都维护了一个双端队列，用来存储需要执行的任务。工作窃取算法允许空闲的线程从其它线程的双端队列中窃取一个任务来执行。窃取的任务必须是最晚的任务，避免和队列所属线程发生竞争。例如下图中，Thread2 从 Thread1 的队列中拿出最晚的 Task1 任务，Thread1 会拿出 Task2 来执行，这样就避免发生竞争。但是如果队列中只有一个任务时还是会发生竞争。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/e19452dd-220a-4a6b-bcb0-91ad5e5c4706.png"/> </div>

## 十、生产者与消费者模式

生产者-消费者模式是一个十分经典的多线程并发协作的模式，弄懂生产者-消费者问题能够让我们对并发编程的理解加深。

所谓生产者-消费者问题，实际上主要是包含了两类线程，一种是**生产者线程用于生产数据**，另一种是**消费者线程用于消费数据**，为了解耦生产者和消费者的关系，通常会采用**共享数据区域**。

共享数据区域就像是一个仓库，生产者生产数据之后直接放置在共享数据区中，并不需要关心消费者的行为；消费者只需要从共享数据区中去获取数据，就不再需要关心生产者的行为。但是，这个共享数据区域中应该具备这样的线程间并发协作的功能：

- 如果共享数据区已满的话，阻塞生产者继续生产数据放置入内
- 如果共享数据区为空的话，阻塞消费者继续消费数据

![](https://gitee.com/duhouan/ImagePro/raw/master/pics/concurrent/c_4.png)

### wait() / notify() 潜在的一些问题

> **notify()  早期通知**

线程 A 还没开始 wait 的时候，线程 B 已经 notify 了。线程 B 通知是没有任何响应的，当线程 B 退出同步代码块后，线程 A 再开始 wait，便会一直阻塞等待，直到被别的线程打断。

```java
public class EarlyNotify {
    public static void main(String[] args) {
        final Object lock = new Object();

        Thread notifyThread = new Thread(new NotifyThread(lock),"notifyThread");
        Thread waitThread = new Thread(new WaitThread(lock),"waitThread");
        notifyThread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitThread.start();
    }

    private static class WaitThread implements Runnable{
        private Object lock;

        public WaitThread(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread().getName() + "  进去代码块");
                    System.out.println(Thread.currentThread().getName() + "  开始 wait");
                    lock.wait();
                    System.out.println(Thread.currentThread().getName() + "   结束 wait");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class NotifyThread implements Runnable{
        private Object lock;

        public NotifyThread(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "  进去代码块");
                System.out.println(Thread.currentThread().getName() + "  开始 notify");
                lock.notify();
                System.out.println(Thread.currentThread().getName() + "  结束开始 notify");
            }
        }
    }
}
```

```html
notifyThread  进去代码块
notifyThread  开始 notify
notifyThread  结束开始 notify
waitThread  进去代码块
waitThread  开始 wait
```

示例中开启了两个线程，一个是 WaitThread，另一个是 NotifyThread。NotifyThread 先启动，先调用 notify() 方法。然后 WaitThread 线程才启动，调用 wait() 方法，但是由于已经通知过了，wait() 方法就无法再获取到相应的通知，因此 WaitThread 会一直在 wait() 方法处阻塞，这种现象就是**通知过早**的现象。

这种现象的解决方法：添加一个状态标志，让 WaitThread 调用 wait() 方法前先判断状态是否已经改变，如果通知早已发出的话，WaitThread 就不再调用 wait() 方法。对上面的代码进行更正：

```java
public class EarlyNotify2 {
    private static boolean isWait = true; //isWait 判断线程是否需要等待

    public static void main(String[] args) {
        final Object lock = new Object();

        Thread notifyThread = new Thread(new NotifyThread(lock),"notifyThread");
        Thread waitThread = new Thread(new WaitThread(lock),"waitThread");
        notifyThread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitThread.start();
    }

    private static class WaitThread implements Runnable{
        private Object lock;

        public WaitThread(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    System.out.println(Thread.currentThread().getName() + "  进去代码块");
                    while (isWait){
                        System.out.println(Thread.currentThread().getName() + "  开始 wait");
                        lock.wait();
                        System.out.println(Thread.currentThread().getName() + "  结束 wait");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class NotifyThread implements Runnable{
        private Object lock;

        public NotifyThread(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + "  进去代码块");
                System.out.println(Thread.currentThread().getName() + "  开始 notify");
                lock.notify();
                isWait = false; //已经唤醒了
                System.out.println(Thread.currentThread().getName() + "  结束开始 notify");
            }
        }
    }
}
```

```html
notifyThread  进去代码块
notifyThread  开始 notify
notifyThread  结束开始 notify
waitThread  进去代码块
```

增加了一个 isWait 状态变量，NotifyThread 调用 notify() 方法后会对状态变量进行更新，在 WaitThread 中调用wait() 方法之前会先对状态变量进行判断，在该示例中，调用 notify() 后将状态变量 isWait 改变为 false ，因此，在 WaitThread 中 while 对 isWait 判断后就不会执行 wait 方法，从而**避免了Notify过早通知造成遗漏的情况。**

小结：在使用线程的等待 / 通知机制时，一般都要配合一个 boolean 变量值（或者其他能够判断真假的条件），在 notify 之前改变该 boolean 变量的值，让 wait 返回后能够退出 while 循环（一般都要在 wait 方法外围加一层 while 循环，以防止早期通知），或在通知被遗漏后，不会被阻塞在 wait() 方法处。这样便保证了程序的正确性。

> **等待 wait 的条件发生变化**

如果线程在等待时接受到了通知，但是之后**等待的条件**发生了变化，并没有再次对等待条件进行判断，也会导致程序出现错误。

```java
public class ConditionChange {
    public static void main(String[] args) {
        final List<String> lock = new ArrayList<>();
        Thread consumer1 = new Thread(new Consumer(lock),"consume1");
        Thread consumer2 = new Thread(new Consumer(lock),"consume1");
        Thread producer = new Thread(new Producer(lock),"producer");
        consumer1.start();
        consumer2.start();
        producer.start();
    }

    static class Consumer implements Runnable{
        private List<String> lock;

        public Consumer(List lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    //这里使用if的话，就会存在 wait 条件变化造成程序错误的问题
                    if(lock.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + " list 为空");
                        System.out.println(Thread.currentThread().getName() + " 调用 wait 方法");
                        lock.wait();
                        System.out.println(Thread.currentThread().getName() + " wait 方法结束");
                    }
                    String element = lock.remove(0);
                    System.out.println(Thread.currentThread().getName() + " 取出第一个元素为：" + element);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Producer implements Runnable{
        private List<String> lock;

        public Producer(List lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 开始添加元素");
                lock.add(Thread.currentThread().getName());
                lock.notifyAll();
            }
        }
    }
}
```

```html
consume1 list 为空
consume1 调用 wait 方法
consume2 list 为空
consume2 调用 wait 方法
producer 开始添加元素
consume2 wait 方法结束
Exception in thread "consume1" consume2 取出第一个元素为：producer
java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
consume1 wait 方法结束
```

异常原因分析：例子中一共开启了 3 个线程：consumer1，consumer2 以及 producer。

首先 consumer1调用了 wait 方法后，线程处于了 WAITTING 状态，并且将对象锁释放出来。consumer2 能够获取对象锁，从而进入到同步代块中，当执行到 wait 方法时，同样的也会释放对象锁。productor 能够获取到对象锁，进入到同步代码块中，向 list 中插入数据后，通过 notifyAll 方法通知处于 WAITING 状态的 consumer1 和consumer2 。consumer2 得到对象锁后，从 wait 方法出退出，退出同步块，释放掉对象锁，然后删除了一个元素，list 为空。这个时候 consumer1 获取到对象锁后，从 wait 方法退出，继续往下执行，这个时候consumer1 再执行 

```java
lock.remove(0) 
```

就会出错，因为 list 由于 consumer2 删除一个元素之后已经为空了。

解决方案：通过上面的分析，可以看出 consumer1 报异常是因为线程从 wait 方法退出之后没有再次对 wait 条件进行判断，因此，此时的 wait 条件已经发生了变化。解决办法就是，在 wait 退出之后再对条件进行判断即可。

```java
public class ConditionChange2 {
    public static void main(String[] args) {
        final List<String> lock = new ArrayList<>();
        Thread consumer1 = new Thread(new Consumer(lock),"consume1");
        Thread consumer2 = new Thread(new Consumer(lock),"consume2");
        Thread producer = new Thread(new Producer(lock),"producer");
        consumer1.start();
        consumer2.start();
        producer.start();
    }

    static class Consumer implements Runnable{
        private List<String> lock;

        public Consumer(List lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                try {
                    //这里使用if的话，就会存在 wait 条件变化造成程序错误的问题
                    while(lock.isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + " list 为空");
                        System.out.println(Thread.currentThread().getName() + " 调用 wait 方法");
                        lock.wait();
                        System.out.println(Thread.currentThread().getName() + " wait 方法结束");
                    }
                    String element = lock.remove(0);
                    System.out.println(Thread.currentThread().getName() + " 取出第一个元素为：" + element);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Producer implements Runnable{
        private List<String> lock;

        public Producer(List lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " 开始添加元素");
                lock.add(Thread.currentThread().getName());
                lock.notifyAll();
            }
        }
    }
}
```

```html
consume1 list 为空
consume1 调用 wait 方法
consume2 list 为空
consume2 调用 wait 方法
producer 开始添加元素
consume2 wait 方法结束
consume2 取出第一个元素为：producer
consume1 wait 方法结束
consume1 list 为空
consume1 调用 wait 方法
```

上面的代码与之前的代码仅仅只是将 wait() 外围的 if 语句改为 while 循环即可，这样当 list 为空时，线程便会继续等待，而不会继续去执行删除 list 中元素的代码。

小结：在使用线程的等待/通知机制时，一般都要在 while 循环中调用 wait()方法，配合使用一个 boolean 变量（或其他能判断真假的条件，如本文中的 list.isEmpty()）。在满足 while 循环的条件时，进入 while 循环，执行 wait()方法，不满足 while 循环的条件时，跳出循环，执行后面的代码。

> **”假死“状态**

如果是多消费者和多生产者情况，如果使用 notify() 方法可能会出现"假死"的情况，即唤醒的是同类线程。

原因分析：假设当前多个生产者线程会调用 wait 方法阻塞等待，当其中的生产者线程获取到对象锁之后使用notify 通知处于 WAITTING 状态的线程，如果唤醒的仍然是生产者线程，就会造成所有的生产者线程都处于等待状态。

解决办法：将 notify() 方法替换成 notifyAll() 方法，如果使用的是 lock 的话，就将 signal() 方法替换成 signalAll()方法。

> **Object提供的消息通知机制总结**

- 永远在 while 循环中对条件进行判断而不是 if 语句中进行 wait 条件的判断
- 使用 notifyAll() 而不是使用 notify()

基本的使用范式如下：

```java
// The standard idiom for calling the wait method in Java 
synchronized (sharedObject) { 
    while (condition) { 
    	sharedObject.wait(); 
        // (Releases lock, and reacquires on wakeup) 
    } 
    // do action based upon condition e.g. take or put into queue 
}
```

### 1、wait() / notifyAll() 实现生产者-消费者

```java
public class ProducerConsumer {
    public static void main(String[] args) {
        final LinkedList linkedList = new LinkedList();
        final int capacity = 5;
        Thread producer = new Thread(new Producer(linkedList,capacity),"producer");
        Thread consumer = new Thread(new Consumer(linkedList),"consumer");

        producer.start();
        consumer.start();
    }

    static class Producer implements Runnable {
        private List<Integer> list;
        private int capacity;

        public Producer(List list, int capacity) {
            this.list = list;
            this.capacity = capacity;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (list) {
                    try {
                        while (list.size() == capacity) {
                            System.out.println("生产者 " + Thread.currentThread().getName() + "  list 已达到最大容量，进行 wait");
                            list.wait();
                            System.out.println("生产者 " + Thread.currentThread().getName() + "  退出 wait");
                        }
                        Random random = new Random();
                        int i = random.nextInt();
                        System.out.println("生产者 " + Thread.currentThread().getName() + " 生产数据" + i);
                        list.add(i);
                        list.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    static class Consumer implements Runnable {
        private List<Integer> list;

        public Consumer(List list) {
            this.list = list;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (list) {
                    try {
                        while (list.isEmpty()) {
                            System.out.println("消费者 " + Thread.currentThread().getName() + "  list 为空，进行 wait");
                            list.wait();
                            System.out.println("消费者 " + Thread.currentThread().getName() + "  退出wait");
                        }
                        Integer element = list.remove(0);
                        System.out.println("消费者 " + Thread.currentThread().getName() + "  消费数据：" + element);
                        list.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
```

输出结果：

```html
生产者 producer 生产数据-1652445373
生产者 producer 生产数据1234295578
生产者 producer 生产数据-1885445180
生产者 producer 生产数据864400496
生产者 producer 生产数据621858426
生产者 producer  list 已达到最大容量，进行 wait
消费者 consumer  消费数据：-1652445373
消费者 consumer  消费数据：1234295578
消费者 consumer  消费数据：-1885445180
消费者 consumer  消费数据：864400496
消费者 consumer  消费数据：621858426
消费者 consumer  list 为空，进行 wait
生产者 producer  退出 wait
```

### 2、await() / signalAll() 实现生产者-消费者

```java
public class ProducerConsumer {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition full = lock.newCondition();
    private static Condition empty = lock.newCondition();

    public static void main(String[] args) {
        final LinkedList linkedList = new LinkedList();
        final int capacity = 5;
        Thread producer = new Thread(new Producer(linkedList,capacity,lock),"producer");
        Thread consumer = new Thread(new Consumer(linkedList,lock),"consumer");

        producer.start();
        consumer.start();
    }

    static class Producer implements Runnable {
        private List<Integer> list;
        private int capacity;
        private Lock lock;

        public Producer(List list, int capacity,Lock lock) {
            this.list = list;
            this.capacity = capacity;
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                    try {
                        while (list.size() == capacity) {
                            System.out.println("生产者 " + Thread.currentThread().getName() + "  list 已达到最大容量，进行 wait");
                            full.await();
                            System.out.println("生产者 " + Thread.currentThread().getName() + "  退出 wait");
                        }
                        Random random = new Random();
                        int i = random.nextInt();
                        System.out.println("生产者 " + Thread.currentThread().getName() + " 生产数据" + i);
                        list.add(i);
                        empty.signalAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        lock.unlock();
                }

            }
        }
    }

    static class Consumer implements Runnable {
        private List<Integer> list;
        private Lock lock;

        public Consumer(List list,Lock lock) {
            this.list = list;
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    while (list.isEmpty()) {
                        System.out.println("消费者 " + Thread.currentThread().getName() + "  list 为空，进行 wait");
                        empty.await();
                        System.out.println("消费者 " + Thread.currentThread().getName() + "  退出wait");
                    }
                    Integer element = list.remove(0);
                    System.out.println("消费者 " + Thread.currentThread().getName() + "  消费数据：" + element);
                    full.signalAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }
}
```

```html
生产者 producer 生产数据-1748993481
生产者 producer 生产数据-131075825
生产者 producer 生产数据-683676621
生产者 producer 生产数据1543722525
生产者 producer 生产数据804266076
生产者 producer  list 已达到最大容量，进行 wait
消费者 consumer  消费数据：-1748993481
消费者 consumer  消费数据：-131075825
消费者 consumer  消费数据：-683676621
消费者 consumer  消费数据：1543722525
消费者 consumer  消费数据：804266076
消费者 consumer  list 为空，进行 wait
生产者 producer  退出 wait
```

### 3、BlockingQueue 实现生产者-消费者

由于 BlockingQueue 内部实现就附加了两个阻塞操作。即

- 当队列已满时，阻塞向队列中插入数据的线程，直至队列中未满
- 当队列为空时，阻塞从队列中获取数据的线程，直至队列非空时为止

可以利用 BlockingQueue 实现生产者-消费者，**阻塞队列完全可以充当共享数据区域**，就可以很好的完成生产者和消费者线程之间的协作。

```java
public class ProducerConsumer {
    private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        Thread producer = new Thread(new Producer(queue),"producer");
        Thread consumer = new Thread(new Consumer(queue),"consumer");

        producer.start();
        consumer.start();
    }

    static class Producer implements Runnable {
        private BlockingQueue<Integer> queue;

        public Producer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                Random random = new Random();
                int i = random.nextInt();
                System.out.println("生产者" + Thread.currentThread().getName() + "生产数据" + i);
                try {
                    queue.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        private BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue<Integer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Integer element = (Integer) queue.take();
                    System.out.println("消费者" + Thread.currentThread().getName() + "正在消费数据" + element);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

```html
生产者producer生产数据-222876564
消费者consumer正在消费数据-906876105
生产者producer生产数据-9385856
消费者consumer正在消费数据1302744938
生产者producer生产数据-177925219
生产者producer生产数据-881052378
生产者producer生产数据-841780757
生产者producer生产数据-1256703008
消费者consumer正在消费数据1900668223
消费者consumer正在消费数据2070540191
消费者consumer正在消费数据1093187
消费者consumer正在消费数据6614703
消费者consumer正在消费数据-1171326759
```

使用 BlockingQueue 来实现生产者-消费者很简洁，这正是利用了 BlockingQueue 插入和获取数据附加阻塞操作的特性。

## 参考资料

- [Java 并发知识总结](https://github.com/CL0610/Java-concurrency)
- [CS-Notes Java并发](https://github.com/CyC2018/CS-Notes/blob/master/docs/notes/Java%20%E5%B9%B6%E5%8F%91.md)
- 《 Java 并发编程的艺术》
- [剑指Java面试-Offer直通车](https://coding.imooc.com/class/303.html)