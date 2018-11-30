package code_00_thread.threadSynchronization;

/*
 * A:同步代码块的锁对象是谁呢?
 * 		任意对象。
 *
 * B:同步方法的格式及锁对象问题?
 * 		把同步关键字加在方法上。
 *
 * 		同步方法是谁呢?
 * 			this
 *
 * C:静态方法及锁对象问题?
 * 		静态方法的锁对象是谁呢?
 * 			类的字节码文件对象。(反射会讲)
 */
public class SellTicketDemo {
	public static void main(String[] args) {
		// 创建资源对象
		//SellTicket st=new SellTicket();//没有线程同步，有问题
		//SellTicket2 st=new SellTicket2(); //锁对象是任意对象
		//SellTicket3 st=new SellTicket3(); //锁对象是当前对象
		//SellTicket4 st=new SellTicket4(); //锁对象是Class对象
		SellTicket5 st=new SellTicket5();//使用可重入锁

		// 创建三个线程对象
		Thread t1 = new Thread(st, "窗口1");
		Thread t2 = new Thread(st, "窗口2");
		Thread t3 = new Thread(st, "窗口3");

		// 启动线程
		t2.start();
		t3.start();
		t1.start();
	}
}