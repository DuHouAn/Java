package com.southeast.map.linkedhashmap;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheTest {
    /**
     * TODO: LRU缓存
     * 以下是使用 LinkedHashMap 实现的一个 LRU 缓存，设定最大缓存空间MAX_ENTRIES 为 3。
     * 使用 LinkedHashMap 的构造函数将 accessOrder 设置true，开启 LUR 顺序。
     * 覆盖 removeEldestEntry() 方法实现，在节点多于MAX_ENTRIES 就会将最近最久未使用的数据移除。[这里就是头结点first]
     * @param args
     */
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>();
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        cache.get(1); //这里又使用1了
        cache.put(4, "d");
        //======链表顺序 2 3 1 4 ===================
        System.out.println(cache.keySet()); //因为容量为3，则这里就先删除链表首元素2
    }
}

class LRUCache<K, V> extends LinkedHashMap<K, V> {//TODO:使用 LinkedHashMap 实现的一个 LRU 缓存
    private static final int MAX_ENTRIES = 3; //TODO:设定最大缓存空间MAX_ENTRIES 为 3
    protected boolean removeEldestEntry(Map.Entry eldest) {
        //TODO: removeEldestEntry() 在LinkedHashMap中默认为 false -->这里重写了该方法
        return size() > MAX_ENTRIES;
        //TODO:size()>3则返回 true-->就是删除最近最久未使用结点
    }
    LRUCache() {
        super(MAX_ENTRIES, 0.75f, true);
    }
}

