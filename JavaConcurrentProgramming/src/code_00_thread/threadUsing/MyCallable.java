package code_00_thread.threadUsing;

import java.util.concurrent.Callable;

/**
 * 与 Runnable 相比，Callable 可以有返回值，返回值通过 FutureTask 进行封装。
 */
public class MyCallable implements Callable {
    @Override
    public Integer call() throws Exception {
        return 100;
    }
}
