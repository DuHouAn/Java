package com.southeast.list.copyonwritearraylist;

import java.util.List;

/**
 * 写线程-->实际上就是成佛那向list中存入数据
 */
public class WriteThread implements Runnable{
    private List<Integer> list;

    public WriteThread(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        list.add(8);
    }
}
