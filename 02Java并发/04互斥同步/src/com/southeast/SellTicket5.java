package com.southeast;

/**
 * @Version 5
 */
public class SellTicket5 implements Runnable{
	private int tickets = 100; //这一百张票是线程的共有资源

	//创建锁对象
	private Object obj = new Object();

	@Override
	public void run() { //同步方法的锁是this
		// 是为了模拟一直有票
		while (true) {
			synchronized (Thread.class) { //同步锁是一个 字节码对象
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
}
