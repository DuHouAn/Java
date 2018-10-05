package com.southeast;

/**
 * @Version 4
 */
public class SellTicket4 implements Runnable{
	private int tickets = 100; //这一百张票是线程的共有资源

	//创建锁对象
	private Object obj = new Object();

	@Override
	public void run() {
		// 是为了模拟一直有票
		while (true) {
			sellTicket();
		}
	}

	private synchronized void sellTicket(){ //同步方法的锁是this
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
