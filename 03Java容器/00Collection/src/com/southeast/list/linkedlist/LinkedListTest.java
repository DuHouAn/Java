package com.southeast.list.linkedlist;

import java.util.LinkedList;
import java.util.List;

public class LinkedListTest {
    public static void main(String[] args) {
        List<String> linkedlist=new LinkedList<String>();

        linkedlist.add("aaaa");
        linkedlist.add("bbbb");
        linkedlist.add("cccc");
        linkedlist.add("dddd");
        linkedlist.add("eeee");

        for(String s:linkedlist){
            System.out.println(s);
        }

     }
}
