package code_00_container.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 18351 on 2019/1/7.
 */
public class LRUCache<K,V> extends LinkedHashMap<K,V>{
    private static final int MAX_ENTRIES = 3;

    LRUCache(){
        super(MAX_ENTRIES,0.75f,true);
    }

    /**
     * removeEldestEntry() 默认为 false，
     * 如果需要让它为 true，需要继承 LinkedHashMap 并且覆盖这个方法的实现，
     * 这在实现 LRU 的缓存中特别有用，通过移除最近最久未使用的节点，
     * 从而保证缓存空间足够，并且缓存的数据都是热点数据。
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_ENTRIES;
    }

    public static void main(String[] args) {
        LRUCache<Integer,String> cache=new LRUCache<>();
        cache.put(1, "a");
        cache.put(2, "b");
        cache.put(3, "c");
        cache.get(1);
        //LRU  键值1被访问过了，则最近最久未访问的就是2
        cache.put(4, "d");
        System.out.println(cache.keySet());
    }
}