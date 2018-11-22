package code_00_thread.threadLifeCycle;

/**
 *  每个线程执行时都有一个优先级的属性，优先级高的线程可以获得较多的执行机会，而优先级低的线程则获得较少的执行机会。
 *  与线程休眠类似，**线程的优先级仍然无法保障线程的执行次序**。
 *  只不过，优先级高的线程获取CPU资源的概率较大，优先级低的也并非没机会执行。
 */
public class ThreadPriorityExample {
    private static class A extends Thread{
        public A(String name,int priority){
            super(name);
            this.setPriority(priority);
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.getName() + "线程第" + i + "次执行！");
            }
        }
    }

    public static void main(String[] args) {
        A t= new A("Thread-1",10);
        A t2=new A("Thread-2",1);
        t2.start();
        t.start();
    }
}
/**
 * 输出结果：
 Thread-1线程第0次执行！
 Thread-2线程第0次执行！
 Thread-1线程第1次执行！
 Thread-2线程第1次执行！
 Thread-1线程第2次执行！
 Thread-2线程第2次执行！
 Thread-1线程第3次执行！
 Thread-2线程第3次执行！
 Thread-1线程第4次执行！
 Thread-2线程第4次执行！
 */
