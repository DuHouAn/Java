package code_00_thread.threadSynchronization;

/**
 * 在线程中调用另一个线程的join()方法，
 * 会将当前线程挂起，而不是忙等待，直到目标线程结束。
 */
public class JoinMethodExample {
    private static class A extends Thread{
        @Override
        public void run() {
            System.out.println("A");
        }
    }

    private static class B extends Thread{
        private  A a;

        public B(A a){
            this.a=a;
        }

        @Override
        public void run() {
            try {
                a.join();
                //b虽然先启动，但是在b线程中调用了a线程的join()方法，b线程会等待a线程结束才继续执行，
                //因此最后能保证a线程的输出优先于b线程的输出。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B");
        }
    }

    public static void main(String[] args) {
        A a=new A();
        B b=new B(a);

        b.start();
        a.start();
    }
}
