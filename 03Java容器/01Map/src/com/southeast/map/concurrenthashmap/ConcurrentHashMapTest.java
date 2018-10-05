package com.southeast.map.concurrenthashmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        final Map<String, Integer> countMap = new ConcurrentHashMap<>();

        Runnable task=new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    Integer value = countMap.get("a");
                    if (null == value) {
                        countMap.put("a", 1);
                    } else {
                        countMap.put("a", value + 1);
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        for(int i=0;i<100;i++){
            Thread t1=new Thread(task);
            t1.start();
        }
        System.out.println(countMap);
    }
}
