package com.southeast;

/**
 * @Version 6 final
 */
public class SellTicket6 implements Runnable{
	private static int tickets = 100; //这一百张票是线程的共有资源

	//创建锁对象
	private Object obj = new Object();

	@Override
	public void run() { //同步方法的锁是this
		// 是为了模拟一直有票
		int x=0;
		while (true) {
			if(x%2==0){
				synchronized (SellTicket6.class){
					sell();
				}
			}else{
				sellTicket(); //这里该同步静态方法的锁是 该类的字节码文件对象，即 SellTicket6.class
			}
			x++;
		}
	}

	private  static void sell(){
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

	private synchronized static void sellTicket(){
		//静态同步方法就是该类的	字节码文件对象
		sell();
	}
}
