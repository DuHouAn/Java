package com.southeast.type;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ImmutableExample2 {
    public static void main(String[] args) {
        Map<String,String> map=new HashMap<String,String>();
        map.put("name","DuHouAn");

        //Collections.unmodifiableXXX()先对原始的集合进行拷贝，需要对集合进行修改的方法都直接抛出异常
        Map<String,String> unmodifiableMap= Collections.unmodifiableMap(map);

        Set<String> set=unmodifiableMap.keySet();
        for(String name:set){
            String value=unmodifiableMap.get(name);
            System.out.println(name+":"+value);
        }
    }
}
