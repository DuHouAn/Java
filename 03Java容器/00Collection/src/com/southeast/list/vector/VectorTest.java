package com.southeast.list.vector;

import java.util.Vector;

public class VectorTest {
    public static void main(String[] args) {
        Vector<String> vector=new Vector<String>();

        vector.add("aaaa");
        vector.add("bbhb");
        vector.add("cccc");
        vector.add("dddd");
        vector.add("eeee");

        for(String s:vector){
            System.out.println(s);
        }

    }
}
