package com.southeast.ForkJoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * 主要用于并行计算中，
 * 和MapReduce原理类似，都是把大的任务拆分成多个小任务并行计算。
 */
public class ForkJoinExample extends RecursiveTask<Integer> {
    private final int threshold=5; //阈值，表示任务的个数
    private int first;
    private int last;

    public ForkJoinExample(int first, int last) {
        this.first = first;
        this.last = last;
    }

    @Override
    protected Integer compute() {
        int result=0;
        if(last-first<=threshold){
            //任务足够小则直接运算
            for(int i=first;i<=last;i++){
                result+=i;
            }
        }else{
            //拆分成小任务
            int middle=first+(last-first)/2;
            ForkJoinExample leftTask=new ForkJoinExample(first,middle);
            ForkJoinExample rightTask=new ForkJoinExample(middle+1,last);
            leftTask.fork();
            rightTask.fork();
            result=leftTask.join()+rightTask.join();
        }
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinExample example=new ForkJoinExample(1,10000);
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        Future result=forkJoinPool.submit(example);
        System.out.println(result.get());
    }
}
