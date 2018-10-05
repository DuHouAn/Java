package com.southeast.genericMethod;

public class Utils {
    public static <K,V>boolean compare(Pair<K,V> p1,Pair<K,V> p2){ // 返回值类型 <K,V>boolean
        return p1.getKey().equals(p2.getKey()) &&
                p1.getValue().equals(p2.getValue());
    }
}
