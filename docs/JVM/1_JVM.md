## 运行时数据区域

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/5778d113-8e13-4c53-b5bf-801e58080b97.png" width="400px"> </div>

### 程序计数器（Program Counter Register）

- 当前线程所执行的字节码行号指示器（逻辑）
- 通过改变计数器的值来选取下一条需要执行的字节码指令
- 和线程一对一的关系，即“线程私有”
- 对 Java 方法计数，如果是 Native 方法则计数器值为 Undefined
- 只是计数，不会发生内存泄漏

### Java 虚拟机栈

每个 Java 方法在执行的同时会创建一个栈帧用于存储局部变量表、操作数栈、常量池引用等信息。从方法调用直至执行完成的过程，就对应着一个栈帧在 Java 虚拟机栈中入栈和出栈的过程。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/8442519f-0b4d-48f4-8229-56f984363c69.png" width="400px"> </div>

可以通过 -Xss 这个虚拟机参数来指定每个线程的 Java 虚拟机栈内存大小：

```java
java -Xss512M HackTheJava
```

该区域可能抛出以下异常：

- 当线程请求的栈深度超过最大值，会抛出 StackOverflowError 异常；
- 栈进行动态扩展时如果无法申请到足够内存，会抛出 OutOfMemoryError 异常。

> 局部变量表和操作数栈

- 局部变量表：包含方法执行过程中的所有变量
- 操作数栈：入栈、出栈、复制、交换、产生消费变量

```java
public class JVMTest {
    public static int add(int a ,int b) {
        int c = 0;
        c = a + b;
        return c;
    }
}
```

```html
javap -verbose JVMTest
```

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/j_1.png" width="400px"> </div>

解读上述指令：

- stack = 2 说明栈的深度是 2 ；locals = 3 说明有 3 个本地变量 ；args_size = 2 说明该方法需传入 2 个参
- load 指令表示入操作数栈，store 表示出操作数栈

执行 add(1,2)，说明局部变量表和操作数栈的关系：

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/j_2.png" > </div>

### 本地方法栈

本地方法栈与 Java 虚拟机栈类似，它们之间的区别只不过是本地方法栈为本地方法服务。

本地方法一般是用其它语言（C、C++ 或汇编语言等）编写的，并且被编译为基于本机硬件和操作系统的程序，对待这些方法需要特别处理。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/66a6899d-c6b0-4a47-8569-9d08f0baf86c.png" width="300px"> </div>

### 堆

所有对象都在这里分配内存，是垃圾收集的主要区域（"GC 堆"）。

现代的垃圾收集器基本都是采用分代收集算法，其主要的思想是针对不同类型的对象采取不同的垃圾回收算法。可以将堆分成两块：

- 新生代（Young Generation）
- 老年代（Old Generation）

堆不需要连续内存，并且可以动态增加其内存，增加失败会抛出 OutOfMemoryError 异常。

可以通过 -Xms 和 -Xmx 这两个虚拟机参数来指定一个程序的堆内存大小，第一个参数设置初始值，第二个参数设置最大值。

```java
java -Xms1M -Xmx2M HackTheJava
```

> Java 内存分配策略

1、静态存储：编译时确定每个数据目标在运行时的存储空间需求

2、栈式存储：数据区需求在编译时未知，运行时模块入口前确定

3、堆式存储：编译时或运行时模块入口都无法确定，动态分配



> **问题一：堆和栈的联系**

引用对象、数组时，栈里定义变量保存堆中目标的首地址。

<div align="center"> <img src="https://gitee.com/duhouan/ImagePro/raw/master/JVM/j_3.png" > </div>

> **问题二：栈和堆的区别**

- 物理地址

  堆的物理内存分配是不连续的；

  栈的物理内存分配是连续的

- 分配内存

  堆是不连续的，分配的内存是在运行期确定的，大小不固定；

  栈是连续的，分配的内存在编译器就已经确定，大小固定

- 存放内容

  堆中存放的是对象和数组，关注的是数据的存储；

  栈中存放局部变量，关注的是程序方法的执行

- 是否线程私有

  堆内存中的对象对所有线程可见，可被所有线程访问；

  栈内存属于某个线程私有的

- 异常

  栈扩展失败，会抛出 StackOverflowError；

  堆内存不足，会抛出 OutOfMemoryError



### 方法区

用于存放已被加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。

和堆一样不需要连续的内存，并且可以动态扩展，动态扩展失败一样会抛出 OutOfMemoryError 异常。

对这块区域进行垃圾回收的主要目标是对常量池的回收和对类的卸载，但是一般比较难实现。

HotSpot 虚拟机把它当成永久代来进行垃圾回收。但很难确定永久代的大小，因为它受到很多因素影响，并且每次 Full GC 之后永久代的大小都会改变，所以经常会抛出 OutOfMemoryError 异常。为了更容易管理方法区，从 JDK 1.8 开始，移除永久代，并把方法区移至元空间，它位于本地内存中，而不是虚拟机内存中。

方法区是一个 JVM 规范，永久代与元空间都是其一种实现方式。在 JDK 1.8 之后，原来永久代的数据被分到了堆和元空间中。**元空间存储类的元信息，静态变量和常量池等放入堆中**。

> 元空间（MetaSpace）与永久代（PermGen）的区别

元空间使用本地内存，而永久代使用 JVM 的内存。

> 元空间（MetaSpace）相比永久代（PermGen）的优势

1、字符串常量池存在永久代中，容易出现性能问题和内存溢出

2、类和方法的信息大小难以确定，给永久代的大小指定带来困难

3、永久代会为 GC 带来不必要的复杂性

### 运行时常量池

运行时常量池是方法区的一部分。

Class 文件中的常量池（编译器生成的字面量和符号引用）会在类加载后被放入这个区域。

除了在编译期生成的常量，还允许动态生成，例如 String 类的 intern()。

### 直接内存

在 JDK 1.4 中新引入了 NIO 类，它可以使用 Native 函数库直接分配堆外内存，然后通过 Java 堆里的 DirectByteBuffer 对象作为这块内存的引用进行操作。这样能在一些场景中显著提高性能，因为避免了在堆内存和堆外内存来回拷贝数据。

### JVM常见参数

| 参数 | 含义                                                         |
| ---- | ------------------------------------------------------------ |
| -Xss | 规定了每个线程虚拟机栈（堆栈）的大小，会影响并发线程数的大小 |
| -Xms | 堆的初始值                                                   |
| -Xmx | 堆能达到的最大值                                             |