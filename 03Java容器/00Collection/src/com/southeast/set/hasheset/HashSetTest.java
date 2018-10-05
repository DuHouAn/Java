package com.southeast.set.hasheset;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HashSetTest {
    public static void main(String[] args) {
        /**
         * HashSet 是一个没有重复元素的集合。
         * *它是由HashMap实现的，不保证元素的顺序，而且HashSet允许使用 null 元素。
         */
        Set<String> set=new HashSet<String>();

        set.add("aaaa");
        set.add("bbbb");
        set.add("cccc");
        set.add("aaaa");
        set.add(null); //允许存入为空，为什么？？

        System.out.println("=====遍历set=====");
        Iterator<String> it=set.iterator();
        while(it.hasNext()){
            String s=it.next();
            System.out.println(s);
        }

        System.out.println("=====遍历set=====");
        for(Iterator<String> its=set.iterator();its.hasNext();){
            String s=its.next();
            System.out.println(s);
        }

        //遍历set
        System.out.println("=====遍历set=====");
        for(String s:set){
            System.out.println(s);
        }

        System.out.println("==转化为数组后，遍历数组===");
        Object[] strs=set.toArray();
        for(Object s:strs){
            System.out.println(s);
        }
    }
}
