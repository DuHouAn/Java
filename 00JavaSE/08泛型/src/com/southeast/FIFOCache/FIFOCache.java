package com.southeast.FIFOCache;

import com.southeast.LRUCache.LRUCache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class FIFOCache<K,V> {
    private final int MAX_CACHE_SIZE;
    private final float DEFAULT_LOAD_FACTORY = 0.75f;

    LinkedHashMap<K, V> map;

    public FIFOCache(int cacheSize) {
        this.MAX_CACHE_SIZE = cacheSize;
        int capacity = (int)Math.ceil(MAX_CACHE_SIZE / DEFAULT_LOAD_FACTORY) + 1;
        map=new LinkedHashMap<K,V>(capacity,DEFAULT_LOAD_FACTORY,false){
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
    }

    public synchronized void put(K key,V value){
        map.put(key,value);
    }

    public synchronized V get(K key){
        return map.get(key);
    }

    public synchronized void remove(K key){
        map.remove(key);
    }

    public synchronized Set<Map.Entry<K,V>> getAll(){
        return map.entrySet();
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        for(Map.Entry<K,V> entry:map.entrySet()){
            sb.append(String.format("%s: %s\n",entry.getKey(),entry.getValue()));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        //按照插入顺序
        FIFOCache<Integer,Integer> fifo=new FIFOCache<Integer, Integer>(5);
        fifo.put(1, 1);
        fifo.put(2, 2);
        fifo.put(3, 3);
        System.out.println(fifo);
        fifo.get(1);
        System.out.println(fifo);

        fifo.put(4,4);
        fifo.put(5,5);
        fifo.put(6,6);
        System.out.println(fifo);
    }
}
