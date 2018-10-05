package com.southeast.map.weekhashmap;

import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class WeekHashMapTest {
    public static void main(String[] args) {
       Map<Integer,String> map=new WeakHashMap<Integer,String>();

       map.put(1,"aaa");
       map.put(2,"bbb");
       map.put(3,"ccc");

        Set<Map.Entry<Integer, String>> entrySet = map.entrySet();
        for(Map.Entry<Integer,String> me:entrySet){
            Integer key=me.getKey();
            String value=me.getValue();
            System.out.println(key+"="+value);
        }
    }
}
