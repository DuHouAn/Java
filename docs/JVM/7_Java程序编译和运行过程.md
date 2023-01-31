# Java 程序编译和运行过程

Java程序从 \.java 文件创建到程序运行要经过两大过程：

-  \.java 文件由编译器编译成 \.class文件
- 字节码由 JVM 解释运行

## 编译过程

.java 源文件会被 Java编译器进行编译为.class文件：

- Java 编译一个类时，如果这个类所依赖的类还没有被编译，编译器会自动的先编译这个所依赖的类，然后引用。如果 Java 编译器在指定的目录下找不到该类所依赖的类的 \.class文件或者 \.java源文件，则会报
  "Cant found sysbol" 的异常错误。
- 编译后的 \.class 文件主要分为两部分：常量池和方法表集合。
  常量池记录的是代码出现过的（常量、类名、成员变量等）以及符号引用（类引用、方法引用，成员变量引用等）；
  方法表集合则记录各个方法的字节码。



## 运行过程

JVM 并不是在运行时就会把所有使用到的类都加载到内存中，而是用到的时候，才加载进方法区，并且只加载一次。
Java类运行的过程大概分为两个步骤：

- 类加载
- 执行类

举例说明 Java 程序运行过程：

```java
public class Person {
    private String name;

    public Person(String name){
        this.name=name;
    }

    public void sayHello(){
        System.out.println("Hello! My Name is: " + name);
    }
}
```

```java
public class JVMTest {
    public static void main(String[] args) {
        Person p=new Person("Li Ming");
        p.sayHello();
    }
}
```

### 1. 类加载

首先编译 JVMTest.java 文件得到 JVMTest.class 文件，系统启动一个 JVM 进程，从 classpath 路径中找到 JVMTest.class 文件，将 JVMTest 的类信息加载到方法区中，这个过程称为   JVMTest 类的加载。

（只有类信息在方法区中，才能创建对象，使用类中的成员变量）

### 2. JVM 找 main() 方法入口

在 main() 方法 入口持有一个指向当前类 (JVMTest) 常量池的指针，常量池中的第一项是一个对 Person 对象的符号引用。

main 方法中 `Person p=new Person("Li Ming")，JVM 需要创建一个 Person 对象，但是此时方法区中是没有 Person 类信息的，所以 JVM 需要加载 Person 类，将 Person 类的信息加载到方法区中。

JVM 以一个直接指向方法区 Person 类的指针替换了常量池中第一项的符号引用。

### 3. 实例化对象

加载完 Person 类的信息以后，JVM 就会在堆中为一个 Person 实例分配内存，然后调用构造方法初始化 Person 实例，并且该实例**持有指向方法区中的 Person 类的类型信息（其中包括方法表）的引用**。

（p 为指向该 Person 实例的引用，会被放到栈中）

### 4. 运行方法

执行 p.sayHello()，JVM 根据栈中 p 的引用找到 Person 对象，然后根据 Person 对象持有的引用定位到方法区中 Person 类类信息的**方法表**，获得 sayHello 方法的字节码地址，然后开始运行方法。

<div align="center">
   <img src="https://github.com/DuHouAn/ImagePro/raw/master/JVM/9bbddeeb-e939-41f0-8e8e-2b1a0aa7e0a7.png" width="700px"/>
</div>


# 补充

- [main() 方法详解](https://www.cnblogs.com/bingyimeiling/p/10409728.html)