# LRU

## LRU 缓存机制(146)

[146. LRU缓存机制](https://leetcode-cn.com/problems/lru-cache/)

```java
class LRUCache {
  private List<Integer> list; //使用 LinkedList 来代替双向链表，维护数据顺序
  private Map<Integer,Integer> map; //使用 map 保存数据，实现 O(1) 的访问
  private int size;//链表长度
  private int capacity;//缓存总量，方便后面判断是否链表长度是否超过缓存容量

  public LRUCache(int capacity) {
    list = new LinkedList<>();
    map = new HashMap<>();
    size=0;
    this.capacity = capacity;
  }

  //判断 key 在 HashMap 中是否存在，
  //如果存在，则把对应的节点移动到链表头部，并返回对应的 value 值;
  //如果不存在，则返回-1。
  public int get(int key) {
    if(!map.containsKey(key)){
      return -1;
    }
    //将对应节点移动到链表头部
    list.remove((Integer)key);
    list.add(0,key);
    return map.get(key);
  }

  //如果 key 在 HashMap 中存在，则先重置对应的 value 值，将节点移动到链表的头部；
  //如果 key 在 HashMap 中不存在，则新建一个节点，并将节点放到链表的头部。
  //注意：当 Cache 存满的时候，将链表最后一个节点删除。
  public void put(int key, int value) {
    if(map.containsKey(key)){
      map.put(key,value);
      //将节点移到链表的头部
      list.remove((Integer)key);
      list.add(0,key);
    }else{
      list.add(0,key);
      map.put(key,value);
      size++;
      if(size>capacity){ //每次插入新元素，都会检查是否超过 capacity,只会多出一个元素
        //如果超过容量，则删除链表最后一个元素，同时要去相应的 map 中删除元素
        int lastElement = list.remove(list.size()-1);
        map.remove(lastElement);
      }
    }
  }
}

@Test
public void test(){
  LRUCache cache = new LRUCache( 2);

  cache.put(1, 1);
  cache.put(2, 2);
  System.out.println(cache.get(1));       // 返回  1
  cache.put(3, 3);    // 该操作会使得密钥 2 作废
  System.out.println(cache.get(2));       // 返回 -1 (未找到)
  cache.put(4, 4);    // 该操作会使得密钥 1 作废
  System.out.println(cache.get(1));       // 返回 -1 (未找到)
  System.out.println(cache.get(3));       // 返回  3
  System.out.println(cache.get(4));       // 返回  4
}
```