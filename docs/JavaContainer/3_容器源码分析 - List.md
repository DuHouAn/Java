# 容器源码分析 - List

如果没有特别说明，以下源码分析基于 JDK 1.8。

## ArrayList

### 1. 概览

实现了 RandomAccess 接口，因此支持随机访问。这是理所当然的，因为 ArrayList 是**基于数组实现**的。

```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
```

数组的默认大小为 10。

```java
private static final int DEFAULT_CAPACITY = 10;
```

### 2. 扩容

添加元素时使用 ensureCapacityInternal() 方法来保证容量足够，如果不够时，需要使用 grow() 方法进行扩容，
新容量的大小为 `oldCapacity + (oldCapacity >> 1)`，也就是**旧容量的 1.5 倍**。

扩容操作需要调用 `Arrays.copyOf()` 把原数组整个复制到新数组中，这个操作代价很高，因此最好在创建 ArrayList 对象时就指定大概的容量大小，减少扩容操作的次数。

```java
public boolean add(E e) {
    //添加元素时使用 ensureCapacityInternal() 方法来保证容量足够，
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}

private void ensureCapacityInternal(int minCapacity) {
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
    }
    ensureExplicitCapacity(minCapacity);
}

private void ensureExplicitCapacity(int minCapacity) {
    modCount++;
    // overflow-conscious code
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}

// grow() 方法进行扩容
private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    //这个操作代价很高，因此最好在创建 ArrayList 对象时就指定大概的容量大小，减少扩容操作的次数。
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```

### 3. 删除元素

需要调用 System.arraycopy() 将 index+1 后面的元素都向左移动一位，该操作的时间复杂度为 O(N)，可以看出 ArrayList 删除元素的代价是非常高的。

```java
public E remove(int index) {
    rangeCheck(index);
    modCount++;
    E oldValue = elementData(index);
    //index+1 后面的元素都向左移动一位 即index+1位置的后面元素个数 (size-1)-(index+1)+1
    int numMoved = size - index - 1;
    if (numMoved > 0)
        //将 index+1后面的元素都向左移动一位，原来的 （index+1）位置元素就移到 index位置
        System.arraycopy(elementData, index+1, elementData, index, numMoved);
    elementData[--size] = null; // clear to let GC do its work
    return oldValue;
}
```

### 4. Fail-Fast

modCount 用来记录 ArrayList 结构发生变化的次数。结构发生变化是指添加或者删除至少一个元素的所有操作，或者是调整内部数组的大小，仅仅只是设置元素的值不算结构发生变化。

在进行序列化或者迭代等操作时，需要比较操作前后 modCount 是否改变，
如果改变了需要抛出 ConcurrentModificationException。

```java
private void writeObject(java.io.ObjectOutputStream s)
    throws java.io.IOException{
    // Write out element count, and any hidden stuff
    //这里 记录操作前的 modCount
    int expectedModCount = modCount;
    s.defaultWriteObject();

    // Write out size as capacity for behavioural compatibility with clone()
    s.writeInt(size);

    // Write out all elements in the proper order.
    for (int i=0; i<size; i++) {
        s.writeObject(elementData[i]);//操作
    }

    //这里的modCount是操作后的 modCount与之前的作比较
    if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
    }
}
```

### 5. 序列化

ArrayList 基于数组实现，并且具有动态扩容特性，因此保存元素的数组不一定都会被使用，那么就没必要全部进行序列化。

保存元素的数组 elementData 使用 transient 修饰，该关键字声明数组默认不会被序列化。

```java
transient Object[] elementData; // non-private to simplify nested class access
```

ArrayList 实现了 writeObject() 和 readObject() 来**只序列化数组中有元素填充那部分内容**。

序列化时需要使用 ObjectOutputStream 的 writeObject() 将对象转换为字节流并输出。

```java
private void writeObject(java.io.ObjectOutputStream s)
    throws java.io.IOException{
    // Write out element count, and any hidden stuff
    int expectedModCount = modCount;
    s.defaultWriteObject();

    // Write out size as capacity for behavioural compatibility with clone()
    s.writeInt(size);

    // Write out all elements in the proper order.
    for (int i=0; i<size; i++) {
        // 使用 ObjectOutputStream 的 writeObject() 将对象转换为字节流并输出。
        s.writeObject(elementData[i]);
    }

    if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
    }
}
```

反序列化使用的是 ObjectInputStream 的 readObject() 方法，原理类似。

```java
private void readObject(java.io.ObjectInputStream s)
    throws java.io.IOException, ClassNotFoundException {
    elementData = EMPTY_ELEMENTDATA;

    // Read in size, and any hidden stuff
    s.defaultReadObject();

    // Read in capacity
    s.readInt(); // ignored

    if (size > 0) {
        // be like clone(), allocate array based upon size not capacity
        //根据size来分配内存，来控制只序列化数组中有元素填充那部分内容
        ensureCapacityInternal(size);

        Object[] a = elementData;
        // Read in all elements in the proper order.
        for (int i=0; i<size; i++) {
            // 使用的是 ObjectInputStream 的 readObject() 方法进行反序列化
            a[i] = s.readObject();
        }
    }
}
```

### 6.System.arraycopy()和Arrays.copyOf()方法
Arrays.copyOf() 的源代码内部调用了 System.arraycopy() 方法。

- System.arraycopy() 方法需要目标数组，将原数组拷贝到目标数组里，而且可以选择**拷贝的起点和长度以及放入新数组中的位置**；
- Arrays.copyOf() 是系统自动在内部创建一个数组，并返回这个新创建的数组。

## Vector

### 1. 同步

它的实现与 ArrayList 类似，但是使用了 synchronized 进行同步。

```java
public synchronized boolean add(E e) {
    modCount++;
    ensureCapacityHelper(elementCount + 1);
    elementData[elementCount++] = e;
    return true;
}

public synchronized E get(int index) {
    if (index >= elementCount)
        throw new ArrayIndexOutOfBoundsException(index);

    return elementData(index);
}
```

### 2. 与 ArrayList 的比较

- Vector 是同步的，因此开销就比 ArrayList 要大，访问速度更慢。
最好使用 ArrayList 而不是 Vector，因为同步操作完全可以由程序员自己来控制；
- Vector 每次扩容新容量是旧容量的 2 倍空间，而 ArrayList 是 1.5 倍。

### 3. 替代方案

可以使用 `Collections.synchronizedList();` 得到一个线程安全的 ArrayList。

```java
List<String> list = new ArrayList<>();
List<String> synList = Collections.synchronizedList(list);
```

也可以使用 java.util.concurrent 并发包下的 CopyOnWriteArrayList 类。

```java
List<String> list = new CopyOnWriteArrayList<>();
```

## LinkedList

### 1. 概览

**基于双向链表**实现，使用 Node 存储链表节点信息。

```java
private static class Node<E> {
    E item;
    Node<E> next;
    Node<E> prev;
}
```

每个链表存储了 first 和 last 指针：

```java
transient Node<E> first;
transient Node<E> last;
```

<div align="center"> <img src="../_pics/java-notes/java/49495c95-52e5-4c9a-b27b-92cf235ff5ec.png" width="500"/> </div><br>

### 2. 添加元素
- 将元素添加到链表尾部
```java
public boolean add(E e) {
    linkLast(e);//这里就只调用了这一个方法
    return true;
}
```
```java
/**
 * e作为最后一个元素。
 */
void linkLast(E e) {
    final Node<E> l = last;
    final Node<E> newNode = new Node<>(l, e, null);
    last = newNode;//新建节点,尾指针指向新节点
    //如果是空的双向链表，则该节点既是尾节点，又是头节点
    if (l == null)
        first = newNode;
    else
        l.next = newNode;//指向后继元素也就是指向下一个元素
    size++;
    modCount++;
}
```

- 将元素添加到链表头部
```java
public void addFirst(E e) {
    linkFirst(e);
}
```

```java
/**
* e元素作为头元素
*/
private void linkFirst(E e) {
    final Node<E> f = first;
    final Node<E> newNode = new Node<>(null, e, f);//新建节点，以头节点为后继节点
    first = newNode;
    //如果链表为空，last节点也指向该节点
    if (f == null)
        last = newNode;
    //否则，将头节点的前驱指针指向新节点，也就是指向前一个元素
    else
        f.prev = newNode;
    size++;
    modCount++;
}
```

### 3. 删除指定元素
```java
public boolean remove(Object o) {
    //如果删除对象为null
    if (o == null) {
        //从头开始遍历
        for (Node<E> x = first; x != null; x = x.next) {
            //找到元素
            if (x.item == null) {
               //从链表中移除找到的元素
                unlink(x);
                return true;
            }
        }
    } else {
        //从头开始遍历
        for (Node<E> x = first; x != null; x = x.next) {
            //找到元素
            if (o.equals(x.item)) {
                //从链表中移除找到的元素
                unlink(x);
                return true;
            }
        }
    }
    return false;
}
```

```java
/**
* 注意：这个待删除的节点是不是头节点或者尾节点
*/
E unlink(Node<E> x) {
    // assert x != null;
    final E element = x.item;
    final Node<E> next = x.next;//得到后继节点
    final Node<E> prev = x.prev;//得到前驱节点

    /**
    * 断开与 prev 的联系
    */
    //如果删除的节点是头节点,直接删除该头结点
    if (prev == null) {
        first = next; 
    } else {
        prev.next = next;//将前驱节点的后继节点指向后继节点
        x.prev = null; //TODO:十分重要
    }

    /**
    * 断开与 next 的联系 
    */
    //如果删除的节点是尾节点,直接删除该尾节点
    if (next == null) {
        last = prev;
    } else {
        next.prev = prev;
        x.next = null;
    }

    x.item = null;
    size--;
    modCount++;
    return element;
}
```

### 4. 与 ArrayList 的比较

- ArrayList 基于动态数组实现，LinkedList 基于双向链表实现；
- ArrayList 支持随机访问，LinkedList 不支持；
- LinkedList 在任意位置添加删除元素更快。
