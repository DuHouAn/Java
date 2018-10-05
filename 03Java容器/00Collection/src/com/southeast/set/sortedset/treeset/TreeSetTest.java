package com.southeast.set.sortedset.treeset;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetTest {
    public static void main(String[] args) {
        Set<String> set=new TreeSet<String>();

        set.add("aaa");
        set.add("ccc");
        set.add("sss");
        set.add("ddd");

        for(String s:set){
            System.out.println(s);
        }
    }
}
