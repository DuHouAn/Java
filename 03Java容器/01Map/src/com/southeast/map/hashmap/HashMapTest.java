package com.southeast.map.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapTest {
    public static void main(String[] args) {
        Map<Integer,String> map=new HashMap<Integer, String>();

        map.put(1,"aaaa");
        map.put(2,"bbbb");
        map.put(3,"cccc");
        map.put(1,"bbbb"); //覆盖原来的与之相同的 key 的value
        map.put(4,"dddd");
        map.put(5,"eeee");

        System.out.println("========遍历map 方式一===========");
        Set<Map.Entry<Integer,String>>  set=map.entrySet();
        for(Map.Entry<Integer,String> me:set){
            Integer key=me.getKey();
            String value=me.getValue();
            System.out.println(key+"="+value);
        }

        System.out.println("=========遍历map 方式二===============");
        Set<Integer> si=map.keySet();
        for(Integer key:si){
            String value=map.get(key);
            System.out.println(key+"="+value);
        }
    }
}
