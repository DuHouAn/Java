package com.southeast.list.copyonwritearraylist;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopyOnWriteArrayListTest {
    public static void main(String[] args) {
        //ArrayList类型
        List<Integer> tmpList= Arrays.asList(new Integer[]{1,2});

        //CopyOnWriteArrayList类型
        List<Integer> copyList=new CopyOnWriteArrayList<Integer>(tmpList);

        //2、模拟多线程对list进行读和写
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new ReadThread(copyList));
        executorService.execute(new WriteThread(copyList));
        executorService.execute(new WriteThread(copyList));
        executorService.execute(new WriteThread(copyList));
        executorService.execute(new ReadThread(copyList));
        executorService.execute(new WriteThread(copyList));
        executorService.execute(new ReadThread(copyList));
        executorService.execute(new WriteThread(copyList));

        System.out.println(copyList.size());


    }
}
