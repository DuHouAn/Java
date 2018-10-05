package com.southeast;

/**
 * @Version 2
 */
public class SellTicket2 implements Runnable {
	private int tickets = 100; //这一百张票是线程的共有资源

	@Override
	public void run() {
		// 定义100张票
		// 每个线程进来都会走这里，这样的话，每个线程对象相当于买的是自己的那100张票，这不合理，所以应该定义到外面
		// int tickets = 100;

		// 是为了模拟一直有票
		while (true) {
			if (tickets > 0) {
				// 为了模拟更真实的场景，我们稍作休息
				try {
					Thread.sleep(100); //t1进来了并休息，t2进来了并休息，t3进来了并休息，
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "正在出售第" + (tickets--) + "张票");
			}
		}
	}
}
