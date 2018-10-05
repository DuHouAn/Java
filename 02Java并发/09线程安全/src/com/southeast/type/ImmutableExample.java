package com.southeast.type;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 在多线程环境下，需要对集合进行修改的方法都直接抛出异常。
 * 应当尽量使对象称为不可变，来满足线程安全。
 */
public class ImmutableExample {
    public static void main(String[] args) {
        Map<String,String> map=new HashMap<String,String>();
        Map<String,String> unmodifiableMap= Collections.unmodifiableMap(map);
        unmodifiableMap.put("name","DuHouAn");

        //这里会 抛出 java.lang.UnsupportedOperationException
        //Collections.unmodifiableXXX()先对原始的集合进行拷贝，需要对集合进行修改的方法都直接抛出异常

        Set<String> set=unmodifiableMap.keySet();
        for(String name:set){
            String value=unmodifiableMap.get(name);
            System.out.println(name+"=="+value);
        }
    }
}
