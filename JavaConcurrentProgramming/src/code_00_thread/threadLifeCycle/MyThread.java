package code_00_thread.threadLifeCycle;

/**
 * Created by 18351 on 2018/11/22.
 */
public class MyThread extends Thread{
    public MyThread(String name,int priority){
        super(name);
        this.setPriority(priority);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(this.getName() + "线程第" + i + "次执行！");
            if (i % 5 == 0)
                Thread.yield();
        }
    }
}
