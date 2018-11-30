package code_00_thread.threadSynchronization.synchronizedBlock;

public class NoSyncThreadTest {
    public static void main(String[] args) {
        NoSyncTHread nst=new NoSyncTHread();

        Thread t1=new Thread(nst);
        Thread t2=new Thread(nst);

        t2.start();
        t1.start();
    }
}
