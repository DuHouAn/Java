package com.southeast.list.copyonwritearraylist;

import java.util.List;

/**
 * 读线程--> 实际上就是从list中获取
 */
public class ReadThread implements Runnable{
    private List<Integer> list;

    public ReadThread(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        for(Integer i:list){
            System.out.println("ReadThread:读出的数据是"+i);
        }
    }
}
