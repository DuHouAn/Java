# String 类和常量池

## 1、String 对象的两种创建方式

```java
String str1 = "abcd";
String str2 = new String("abcd");
System.out.println(str1==str2); //false
```

这两种不同的创建方法是有差别的:

第一种方式是在常量池中获取对象("abcd" 属于字符串字面量，因此编译时期会在常量池中创建一个字符串对象)；

第二种方式一共会创建两个字符串对象（前提是 String Pool 中还没有 "abcd" 字符串对象）。

- "abcd" 属于字符串字面量，因此编译时期会在常量池中创建一个字符串对象，该字符串对象指向这个 "abcd" 字符串字面量；
- 使用 new 的方式会在堆中创建一个字符串对象。

str1 指向常量池中的 “abcd”，而 str2 指向堆中的字符串对象。

## 2、intern() 方法

intern() 方法设计的初衷，就是重用 String 对象，以节省内存消耗。

JDK6：当调用intern方法的时候，如果字符串常量池先前已创建出该字符串对象，则返回常量池中的该字符串的引用。否则，将此字符串对象添加到字符串常量池中，并且返回该字符串对象的引用。

JDK6+：当调用intern方法的时候，如果字符串常量池先前已创建出该字符串对象，则返回常量池中的该字符串的引用。**否则，如果该字符串对象已存在与Java堆中，则将堆中对此对象的引用添加到字符串常量池中，并且返回该引用**；如果堆中不存在，则在常量池中创建该字符串并返回其引用。

在 JVM 运行时数据区中的方法区有一个常量池，但是发现在 JDK 1.6 以后常量池被放置在了堆空间，因此常量池位置的不同影响到了 String 的 intern() 方法的表现。

```java
String s = new String("1");
s.intern();
String s2 = "1";
System.out.println(s == s2);
 
String s3 = new String("1") + new String("1");
s3.intern();
String s4 = "11";
System.out.println(s3 == s4);
```

> JDK 1.6 及以下

- 上述代码输出结果：

```html
false
false
```

- 解释：

在 JDK 1.6 中所有的输出结果都是 false，因为 JDK 1.6 以及以前版本中，常量池是放在 PermGen 区（属于方法区）中的，而方法区和堆区是完全分开的。

**使用引号声明的字符串会直接在字符串常量池中生成**的，而 new 出来的 String 对象是放在堆空间中的。所以两者的内存地址肯定是不相同的，即使调用了 intern() 方法也是不影响的。 

intern() 方法在 JDK 1.6 中的作用：比如 `String s = new String("1");`，再调用 `s.intern()`，此时返回值还是字符串"1"，表面上看起来好像这个方法没什么用处。但实际上，在 JDK1.6 中：**检查字符串常量池里是否存在 "1" 这么一个字符串，如果存在，就返回池里的字符串；如果不存在，该方法会把 "1" 添加到字符串常量池中，然后再返回它的引用**。

> JDK 1.6 及以上

- 上述代码输出结果：

```html
false
true
```

- 解释：

`String s= new String("1")` 生成了字符串常量池中的 "1" 和堆空间中的字符串对象。

`s.intern()` s 对象去字符串常量池中寻找后，发现 "1" 已存在于常量池中。

`String s2 = "1"` 生成 s2 的引用指向常量池中的 "1" 对象。

显然，s 和 s2 的引用地址是不同的。

`String s3 = new String("1") + new String("1") `在字符串常量池中生成 "1"，并在堆空间中生成 s3 引用指向的对象（内容为 "11"）。 *注意此时常量池中是没有 "11" 对象*。

`s3.intern()`将 s3 中的 "11" 字符串放入字符串常量池中。 JDK 1.6 的做法是直接在常量池中生成一个 "11" 的对象。但**在 JDK 1.7 中，常量池中不需要再存储一份对象了，可以直接存储堆中的引用**。这份引用直接指向 s3 引用的对象，也就是说 `s3.intern() == s3 `会返回 true。

`String s4 = "11"`， 这一行代码会直接去常量池中创建，但是发现已经有这个对象了，此时 s4 就是指向 s3 引用对象的一个引用。因此 `s3 == s4 `返回了true。

## 3、字符串拼接

```java
String str1 = "str";
String str2 = "ing";
		  
String str3 = "str" + "ing";//常量池中的对象
String str4 = str1 + str2; //TODO:在堆上创建的新的对象	  
String str5 = "string";//常量池中的对象
System.out.println(str3 == str4);//false
System.out.println(str3 == str5);//true
System.out.println(str4 == str5);//false
```

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/j_8.jpg" width="400px"> </div>

注意：尽量避免多个字符串拼接，因为这样会重新创建对象。 如果需要改变字符串的话，可以使用 **StringBuilder** 或者 **StringBuffer**。

> 面试题：String s1 = new String("abc");问创建了几个对象？

创建2个字符串对象（前提是 String Pool 中还没有 "abcd" 字符串对象）。

- "abc" 属于字符串字面量，因此编译时期会**在常量池中创建一个字符串对象**，指向这个 "abcd" 字符串字面量；
- 使用 new 的方式会在堆中创建一个字符串对象。

(字符串常量"abc"在**编译期**就已经确定放入常量池，而 Java **堆上的"abc"是在运行期**初始化阶段才确定)。

```
String s1 = new String("abc");// 堆内存的地址值
String s2 = "abc";
System.out.println(s1 == s2);// 输出false
//因为一个是堆内存，一个是常量池的内存，故两者是不同的。
System.out.println(s1.equals(s2));// 输出true
```