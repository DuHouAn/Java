package code_00_thread.threadLifeCycle;

/**
 * 虽然 ThreadB线程先启动，但是因为在 ThreadB 线程中调用了 ThreadA 线程的 join() 方法，
 * ThreadB 线程会等待 ThreadA 线程结束才继续执行，因此最后能够保证 ThreadA 线程的输出先于 ThreadB 线程的输出。
 */
public class JoinExample {
    private static class ThreadA extends Thread{
        @Override
        public void run() {
            System.out.println("A");
        }
    }

    private static class ThreadB extends Thread{
        private ThreadA a;

        public ThreadB(ThreadA a){
            this.a=a;
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

    public static void main(String[] args) {
        ThreadA a=new ThreadA();
        ThreadB b=new ThreadB(a);
        b.start();
        a.start();
    }
}
//输出结果
//A
//B