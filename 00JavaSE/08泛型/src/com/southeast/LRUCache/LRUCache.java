package com.southeast.LRUCache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LRUCache<K,V> {
    private final int MAX_CACHE_SIZE;
    private final float DEFAULT_LOAD_FACTORY = 0.75f;

    LinkedHashMap<K, V> map;

    public LRUCache(int cacheSize) {
        this.MAX_CACHE_SIZE = cacheSize;
        int capacity=(int)Math.ceil(MAX_CACHE_SIZE/DEFAULT_LOAD_FACTORY)+1;
        //初始化大小设置为(缓存大小 / loadFactor) + 1，这样就可以在元素数目达到缓存大小时，也不会进行扩容
        map=new LinkedHashMap<K,V>(capacity,DEFAULT_LOAD_FACTORY,true){ //true表示按照访问顺序
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size()>MAX_CACHE_SIZE;
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
        LRUCache<Integer,Integer> lru=new LRUCache<Integer, Integer>(5); //该缓存的容量是5
        lru.put(1, 1);
        lru.put(2, 2);
        lru.put(3, 3);
        System.out.println(lru);
        lru.get(1); //这里访问了 key=1的元素
        //按照访问顺序排序 --> key=1的元素是最新才访问的 这是key=2的元素是最近最久未访问的元素
        System.out.println(lru);

        lru.put(4,4);
        lru.put(5,5);
        lru.put(6,6);
        System.out.println(lru);
    }
}
