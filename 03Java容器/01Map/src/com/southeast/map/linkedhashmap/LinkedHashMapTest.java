package com.southeast.map.linkedhashmap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LinkedHashMapTest {
    public static void main(String[] args) {
        Map<Integer, String> map =
                new LinkedHashMap<Integer, String>(16, 0.75f, true);
        map.put(1, "111");
       map.put(2, "222");
       map.put(3, "333");
        map.put(4, "444");

        System.out.println("========遍历map 方式一===========");
        Set<Map.Entry<Integer,String>> set=map.entrySet();
        for(Map.Entry<Integer,String> me:set){
            Integer key=me.getKey();
            String value=me.getValue();
            System.out.println(key+"="+value);
        }
    }
}
