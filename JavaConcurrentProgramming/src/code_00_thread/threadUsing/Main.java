package code_00_thread.threadUsing;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by 18351 on 2018/11/21.
 */
public class Main {
    public static void main(String[] args) {
        testImplementsRunnable();
    }
    /**
     * 测试 实现 Callable 接口
     */
    public static void testImplementCallable() throws ExecutionException, InterruptedException {
        MyCallable mc=new MyCallable();
        FutureTask<Integer> ft=new FutureTask<Integer>(mc);

        Thread t=new Thread(ft);
        t.start();

        System.out.println(ft.get());
    }

    /**
     * 测试 实现 Runnable 接口
     */
    public static void testImplementsRunnable(){
        MyRunnable r=new MyRunnable();
        Thread t=new Thread(r);
        t.start();
    }

    /**
     * 测试 继承 Thread 类
     */
    public static void testExtendsThread(){
        MyThread t=new MyThread();
        t.start();
    }
}
