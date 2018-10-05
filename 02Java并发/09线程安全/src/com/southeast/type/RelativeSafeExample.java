package com.southeast.type;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RelativeSafeExample {
    private static Vector<Integer> vector=new Vector<Integer>();

    public static void main(String[] args) {
        while(true){
            for(int i=0;i<100;i++){
                vector.add(i);
            }

            ExecutorService executorService= Executors.newCachedThreadPool();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<vector.size();i++){
                        vector.remove(i);
                    }
                }
            });

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<vector.size();i++){
                        vector.get(i);
                    }
                }
            });
            executorService.shutdown();
            //发生异常 java.lang.ArrayIndexOutOfBoundsException: Array index out of range: 37
            //因为删除元素的线程已经删除了该元素，但是获取元素的线程视图访问一个已经被删除的元素，
            //就会抛出ArrayIndexOutOfBoundsException
        }
    }
}
