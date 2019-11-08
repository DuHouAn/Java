# 8 种基本类型的包装类和常量池

- Java 基本类型的包装类的大部分都实现了常量池技术， 即Byte，Short，Integer，Long，Character，Boolean； 这 5 种包装类默认创建了数值 **[-128，127]** 的相应类型的缓存数据， 但是超出此范围仍然会去创建新的对象。
- **两种浮点数类型的包装类 Float , Double 并没有实现常量池技术**。

valueOf() 方法的实现比较简单，就是先判断值是否在缓存池中，如果在的话就直接返回缓存池的内容。

Integer 的部分源码：

```
public static Integer valueOf(int i) {
    if (i >= IntegerCache.low && i <= IntegerCache.high)
        return IntegerCache.cache[i + (-IntegerCache.low)];
    return new Integer(i);
}
```

在 Java 8 中，Integer 缓存池的大小默认为 -128~127。

```java
static final int low = -128;
static final int high;
static final Integer cache[];

static {
    // high value may be configured by property
    int h = 127;
    String integerCacheHighPropValue =
        sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
    if (integerCacheHighPropValue != null) {
        try {
            int i = parseInt(integerCacheHighPropValue);
            i = Math.max(i, 127);
            // Maximum array size is Integer.MAX_VALUE
            h = Math.min(i, Integer.MAX_VALUE - (-low) -1);
        } catch( NumberFormatException nfe) {
            // If the property cannot be parsed into an int, ignore it.
        }
    }
    high = h;

    cache = new Integer[(high - low) + 1];
    int j = low;
    for(int k = 0; k < cache.length; k++)
        cache[k] = new Integer(j++);

    // range [-128, 127] must be interned (JLS7 5.1.7)
    assert IntegerCache.high >= 127;
}
```

- 示例1:

```java
Integer i1=40;
//Java 在编译的时候会直接将代码封装成 Integer i1=Integer.valueOf(40);从而使用常量池中的对象。
Integer i2 = new Integer(40);
//创建新的对象。
System.out.println(i1==i2);//输出false
```

- 示例2：Integer有自动拆装箱功能

```java
Integer i1 = 40;
Integer i2 = 40;
Integer i3 = 0;
Integer i4 = new Integer(40);
Integer i5 = new Integer(40);
Integer i6 = new Integer(0);
  
System.out.println("i1=i2   " + (i1 == i2)); //输出 i1=i2  true
System.out.println("i1=i2+i3   " + (i1 == i2 + i3)); //输出 i1=i2+i3  true
//i2+i3得到40,比较的是数值
System.out.println("i1=i4   " + (i1 == i4)); //输出 i1=i4 false
System.out.println("i4=i5   " + (i4 == i5)); //输出 i4=i5 false
//i5+i6得到40，比较的是数值
System.out.println("i4=i5+i6   " + (i4 == i5 + i6)); //输出 i4=i5+i6 true
System.out.println("40=i5+i6   " + (40 == i5 + i6)); //输出 40=i5+i6 true    
```
