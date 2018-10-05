package com.southeast;

/**
 * @Version 1
 */
public class SellTicket implements Runnable {

	// 定义100张票
	// private int tickets = 100;
	// 为了让多个线程对象共享这100张票，我们其实应该用静态修饰
	private static int tickets = 100;

	@Override
	public void run() {
		// 定义100张票
		// 每个线程进来都会走这里，这样的话，每个线程对象相当于买的是自己的那100张票，这不合理，所以应该定义到外面
		// int tickets = 100;

		// 是为了模拟一直有票
		while (true) {
			if (tickets > 0) {
				System.out.println(Thread.currentThread().getName() + "正在出售第" + (tickets--) + "张票");
			}
		}
	}
}
