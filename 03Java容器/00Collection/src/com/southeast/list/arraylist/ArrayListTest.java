package com.southeast.list.arraylist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ArrayListTest {
    public static void main(String[]args){
        ArrayList<String> list=new ArrayList<String>();

        list.add("aaaa");
        list.add("bbbb");
        list.add("cccc");
        list.add("dddd");

        System.out.println("===========迭代器方式遍历==========");
        Iterator<String> it=list.iterator();
        while(it.hasNext()){
            String s=it.next();
            System.out.println(s);
        }

        System.out.println("===========增强for循环方式遍历===========");
        for(String s:list){
            System.out.println(s);
        }

        System.out.println("================================");
        //根据下标删除元素
        list.remove(0);
        for(String s:list){
            System.out.println(s);
        }
    }
}
