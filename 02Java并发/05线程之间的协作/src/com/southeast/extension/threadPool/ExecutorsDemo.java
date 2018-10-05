package com.southeast.extension.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*线程池的好处？
线程池中每一个线程代码结束后，不会死亡，
而且再次回到线程池中成为空闲状态，等待下一个对象来使用。
 * 
 *  如何实现线程的代码呢?
 * 		A:创建一个线程池对象，控制要创建几个线程对象。
 * 			public static ExecutorService newFixedThreadPool(int nThreads)
 * 		B:这种线程池的线程可以执行：
 * 			可以执行Runnable对象或者Callable对象代表的线程
 * 			做一个类实现Runnable接口。
 * 		C:调用如下方法即可
 * 			Future<?> submit(Runnable task)
 *			<T> Future<T> submit(Callable<T> task)
 *		D:我就要结束，可以吗?
 *			可以。 shutDown()
 */
public class ExecutorsDemo {
	public static void main(String[] args) {
		// public static ExecutorService newFixedThreadPool(int nThreads)
		ExecutorService pool = Executors.newFixedThreadPool(2);

		pool.submit(new MyRunnable());
		pool.submit(new MyRunnable());

		pool.shutdown();
	}
}
