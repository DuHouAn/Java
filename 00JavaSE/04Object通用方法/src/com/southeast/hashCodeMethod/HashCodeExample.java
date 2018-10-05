package com.southeast.hashCodeMethod;

import java.util.HashSet;

public class HashCodeExample {
    public static void main(String[] args) {
        EqualExample e1=new EqualExample(1,2,3);
        EqualExample e2=new EqualExample(1,2,3);
        System.out.println(e1.equals(e2));

        HashSet<EqualExample> set=new HashSet<EqualExample>();
        set.add(e1);
        set.add(e2);
        System.out.println(set.size()); //1 --> 没有重写 hashCode 之前是 1
    }
}
