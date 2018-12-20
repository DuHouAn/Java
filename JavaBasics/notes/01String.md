<!-- GFM-TOC -->
* [二、String](#二string)
    * [概览](#概览)
    * [不可变的好处](#不可变的好处)
    * [String, StringBuffer and StringBuilder](#string,-stringbuffer-and-stringbuilder)
    * [String Pool](#string-pool)
    * [new String("abc")](#new-string"abc")
<!-- GFM-TOC -->
# 二、String

## 概览

String 被声明为 final，因此它不可被继承。

**内部使用 char 数组存储数据，该数组被声明为 final**，
这意味着 value 数组初始化之后就不能再引用其它数组。
并且 String 内部没有改变 value 数组的方法，因此可以保证 String 不可变。

```java
public final class String
    implements java.io.Serializable, Comparable<String>, CharSequence {
    /** The value is used for character storage. */
    private final char value[];
```

## 不可变的好处

**1. 可以缓存 hash 值** 

因为 String 的 hash 值经常被使用，例如 String 用做 HashMap 的 key。
不可变的特性可以使得 hash 值也不可变，因此只需要进行一次计算。

**2. String Pool 的需要** 

如果一个 String 对象已经被创建过了，那么就会从 String Pool 中取得引用。
只有 String 是不可变的，才可能使用 String Pool。

<div align="center"> <img src="pics//f76067a5-7d5f-4135-9549-8199c77d8f1c.jpg" width=""/> </div><br>

**3. 安全性** 

String 经常作为参数，String 不可变性可以保证参数不可变。例如在作为网络连接参数的情况下如果 String 是可变的，那么在网络连接过程中，String 被改变，改变 String 对象的那一方以为现在连接的是其它主机，而实际情况却不一定是。

**4. 线程安全** 

String 不可变性天生具备线程安全，可以在多个线程中安全地使用。

[Program Creek : Why String is immutable in Java?](https://www.programcreek.com/2013/04/why-string-is-immutable-in-java/)

## String, StringBuffer and StringBuilder

**1. 可变性** 

- String 不可变
- StringBuffer 和 StringBuilder 可变

**2. 线程安全** 

- String 不可变，因此是线程安全的
- StringBuilder 不是线程安全的
- StringBuffer 是线程安全的，内部使用 synchronized 进行同步

**3. 性能**

- Java中对String对象进行的操作实际上是一个不断创建新的对象并且将旧的对象回收的一个过程，所以执行速度很慢

- StringBuffer每次都会对StringBuffer对象本身进行操作，而不是生成新的对象并改变对象引用

-  StringBuilder每次都会对StringBuilder对象本身进行操作，而不是生成新的对象并改变对象引用。
相同情况下使用StirngBuilder相比使用 StringBuffer 仅能获得 10%~15% 左右的性能提升，但却冒多线程不安全的风险。


> **三者使用的总结**

- 操作少量的数据，使用String
- 单线程操作字符串缓冲区下操作大量数据，使用StringBuilder
- 多线程操作字符串缓冲区下操作大量数据，使用StringBuffer

了解: String和StringBuffer的相互转换

（1）String --> StringBuffer
方式一：构造方法 StringBuffer sb=new StringBuffer(s);

方式二：通过append()方法 StringBuffer sb=new StringBuffer(); sb.append(s);

（2）StringBuffer --> String

方式一：构造方法 String s=new String(buffer);

方式二：通过toString()方法 String s=buffer.toString();

## String Pool

字符串常量池（String Pool）保存着所有字符串字面量（literal strings），这些字面量在编译时期就确定。

不仅如此，还可以使用 String 的 **intern() 方法在运行过程中将字符串添加到 String Pool 中**。

当一个字符串调用 intern() 方法时，如果 String Pool 中已经存在一个字符串和该字符串值相等（使用 equals() 方法进行确定），
那么就会返回 String Pool 中字符串的引用；
否则，就会在 String Pool 中添加一个新的字符串，并返回这个新字符串的引用。

下面示例中，s1 和 s2 采用 new String() 的方式新建了两个不同字符串，
而 s3 和 s4 是通过 s1.intern() 方法取得一个字符串引用。
intern() 首先把 s1 引用的字符串放到 String Pool 中，然后返回这个字符串引用。
因此 s3 和 s4 引用的是同一个字符串。

```java
String s1 = new String("aaa");
String s2 = new String("aaa");
System.out.println(s1 == s2);           // false
String s3 = s1.intern();
String s4 = s1.intern();
System.out.println(s3 == s4);           // true
```

如果是采用 "bbb" 这种字面量的形式创建字符串，会自动地将字符串放入 String Pool 中。

```java
String s5 = "bbb";
String s6 = "bbb";
System.out.println(s5 == s6);  // true
```

在 Java 7 之前，String Pool 被放在运行时常量池中，它属于永久代。
而在 Java 7，String Pool 被移到堆中。这是因为永久代的空间有限，在大量使用字符串的场景下会导致 OutOfMemoryError 错误。

## new String("abc")

使用这种方式一共会创建两个字符串对象（前提是 String Pool 中还没有 "abc" 字符串对象）。

- "abc" 属于字符串字面量，因此编译时期会在 String Pool 中创建一个字符串对象，指向这个 "abc" 字符串字面量；
- 而使用 new 的方式会在堆中创建一个字符串对象。

创建一个测试类，其 main 方法中使用这种方式来创建字符串对象。

```java
public class NewStringTest {
    public static void main(String[] args) {
        String s = new String("abc");
    }
}
```

使用 javap -verbose 进行反编译，得到以下内容：

```java
// ...
Constant pool:
// ...
   #2 = Class              #18            // java/lang/String
   #3 = String             #19            // abc
// ...
  #18 = Utf8               java/lang/String
  #19 = Utf8               abc
// ...

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=3, locals=2, args_size=1
         0: new           #2                  // class java/lang/String
         3: dup
         4: ldc           #3                  // String abc
         6: invokespecial #4                  // Method java/lang/String."<init>":(Ljava/lang/String;)V
         9: astore_1
// ...
```

在 Constant Pool 中，#19 存储这字符串字面量 "abc"，#3 是 String Pool 的字符串对象，它指向 #19 这个字符串字面量。在 main 方法中，0: 行使用 new #2 在堆中创建一个字符串对象，并且使用 ldc #3 将 String Pool 中的字符串对象作为 String 构造函数的参数。

以下是 String 构造函数的源码，可以看到，在将一个字符串对象作为另一个字符串对象的构造函数参数时，并不会完全复制 value 数组内容，而是都会指向同一个 value 数组。

```java
public String(String original) {
    this.value = original.value;
    this.hash = original.hash;
}
```

了解：说说String s=new String("aaa")和String s="aaa"的区别？

- 前者会创建一个或者多个对象
- 后者只会创建一个或者零个对象
    