package com.southeast.set.linkedhashset;

import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashSetTest {
    public static void main(String[] args) {
        /**
         * LinkedHashSet中的元素顺序是可以保证的，
         * 也就是说插入顺序和输出顺序是一致的。
         */
        Set<String> set=new LinkedHashSet<String>();

        set.add("aaa");
        set.add("bbb");
        set.add("ccc");
        set.add("ddd");
        set.add("aaa");

        for(String s:set){
            System.out.println(s);
        }
    }
}
