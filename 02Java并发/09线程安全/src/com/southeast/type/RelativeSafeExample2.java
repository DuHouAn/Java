package com.southeast.type;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 判断线程安全：
 * （1）是否是多线程环境
 * （2）是否有共享数据
 * （3）是否有多条语句操作共享数据
 */
public class RelativeSafeExample2 {
    private static Vector<Integer> vector=new Vector<Integer>();

    public static void main(String[] args) throws InterruptedException {
            for(int i=0;i<100;i++){
                vector.add(i);
            }

            ExecutorService executorService= Executors.newCachedThreadPool();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    synchronized (vector) {
                        for (int i = 0; i < vector.size(); i++) {
                            //vector.remove(i);
                            System.out.println("Remove:"+vector.remove(i));
                        }
                    }
                }
            });

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    synchronized (vector){
                        for(int i=0;i<vector.size();i++){
                            System.out.println("Get:"+vector.get(i));
                        }
                   }
                }
            });
        executorService.shutdown();

        Thread.sleep(5000);
        for(int i=0;i<vector.size();i++){
            System.out.println("Get2:"+vector.get(i));
        }

    }
}
