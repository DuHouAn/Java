<!-- GFM-TOC -->
* [八、final关键字](#八final关键字)
    * [final的简介](#final的简介)
    * [final的具体使用场景](#final的具体使用场景)
        * [final修饰成员变量](#final修饰成员变量)
        * [final修饰局部变量](#final修饰局部变量)
        * [final修饰方法](#final修饰方法)
        * [final修饰类](#final修饰类)
    * [final域重排序规则](#final域重排序规则)
        * [final为基本类型](#final为引用类型)
        * [final为引用类型](#final为引用类型)
    * [对象溢出](#对象溢出)
    * [final的实现原理](#final的实现原理)
<!-- GFM-TOC -->
# 八、final关键字
## final的简介
**final可以修饰变量，方法和类**，用于表示所修饰的内容一旦赋值之后就不会再被改变，比如String类就是一个final类型的类。

## final的具体使用场景
final能够修饰变量，方法和类，也就是final使用范围基本涵盖了java每个地方，
下面就分别以锁修饰的位置：变量，方法和类分别介绍。

### final修饰成员变量
```java
public class FinalExample {
    //声明变量的时候，就进行初始化
    private final int num=6;
    //类变量必须要在静态初始化块中指定初始值或者声明该类变量时指定初始值
    // private final String str; //编译错误：因为非静态变量不可以在静态初始化快中赋初值
    private final static String name;
    private final double score;
    private final char ch;
    //private final char ch2;//编译错误:TODO：因为没有在构造器、初始化代码块和声明时赋值
    
    {
        //实例变量在初始化代码块赋初值
        ch='a';
    }
    
    static {
        name="aaaaa";
    }
    
    public FinalExample(){
        //num=1;编译错误：已经赋值后，就不能再修改了
        score=90.0;
    }
    
    public void ch2(){
        //ch2='c';//编译错误：实例方法无法给final变量赋值
    }
}
```
- 类变量：必须要在静态初始化块中指定初始值或者声明该类变量时指定初始值，而且只能在这两个地方之一进行指定
- 实例变量：必要要在非静态初始化块，声明该实例变量或者在构造器中指定初始值，而且只能在这三个地方进行指定

### final修饰局部变量
final局部变量由程序员进行显式初始化，
如果final局部变量已经进行了初始化则后面就不能再次进行更改，
如果final变量未进行初始化，可以进行赋值，当且仅有一次赋值，一旦赋值之后再次赋值就会出错。

```java
public void test(){
    final int a=1;
    //a=2;//编译错误：final局部变量已经进行了初始化则后面就不能再次进行更改
}
```

> **final基本数据类型 VS final引用数据类型**

如果final修饰的是一个基本数据类型的数据，一旦赋值后就不能再次更改，
那么，如果final是引用数据类型了？这个引用的对象能够改变吗？

```java
public class FinalExample2 {
    private static class Person {
        private String name;
        private int age;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public Person(String name, int age) {
            this.name=name;
            this.age = age;
        }

        @Override
        public String toString() {
            StringBuilder res=new StringBuilder();
            res.append("[").append("name="+name+",age="+age).append("]");
            return res.toString();
        }
    }

    private static final Person person=new Person("小李子",23);

    public static void main(String[] args) {
        System.out.println(person);
        person.setAge(24);
        System.out.println(person);
    }
}
```
输出结果：
```html
[name=小李子,age=23]
[name=小李子,age=24]
```
当我们对final修饰的引用数据类型变量person的属性改成24，是可以成功操作的。
通过这个实验我们就可以看出来当final修饰基本数据类型变量时，不能对基本数据类型变量重新赋值，
因此基本数据类型变量不能被改变。
而对于引用类型变量而言，它仅仅保存的是一个引用，final只保证这个引用类型变量所引用的地址不会发生改变，
即一直引用这个对象，但这个对象属性是可以改变的。

> **宏变量**

利用final变量的不可更改性，在满足以下三个条件时，该变量就会成为一个“宏变量”，即是一个常量。

- 使用final修饰符修饰
- 在定义该final变量时就指定了初始值；
- 该初始值在编译时就能够唯一确定

注意：当程序中其他地方使用该宏变量的地方，编译器会直接替换成该变量的值。

### final修饰方法
> **重写(Override)**

**被final修饰的方法不能够被子类所重写**。
比如在Object中，getClass()方法就是final的，我们就不能重写该方法，
但是hashCode()方法就不是被final所修饰的，我们就可以重写hashCode()方法。

> **重载(Overload)**

**被final修饰的方法是可以重载的**

### final修饰类
**当一个类被final修饰时，该类是不能被子类继承的**。
子类继承往往可以重写父类的方法和改变父类属性，会带来一定的安全隐患，
因此，当一个类不希望被继承时就可以使用final修饰。

> **不可变类**

final经常会被用作不变类上。我们先来看看什么是不可变类：

- 使用private和final修饰符来修饰该类的成员变量
- 提供带参的构造器用于初始化类的成员变量
- 仅为该类的成员变量提供getter方法，不提供setter方法，因为普通方法无法修改fina修饰的成员变量
- 如果有必要就重写Object类的hashCode()和equals()方法，应该保证用equals()判断相同的两个对象其Hashcode值也是相等的。

JDK中提供的八个包装类和String类都是不可变类。

## final域重排序规则
### final为基本类型
```java
public class FinalDemo {
    private int a;  //普通域
    private final int b; //final域-->int基本类型
    private static FinalDemo finalDemo;//引用类型，但不是final修饰的

    public FinalDemo() {
        a = 1; // 1. 写普通域
        b = 2; // 2. 写final域
    }

    public static void writer() {
        finalDemo = new FinalDemo();
    }

    public static void reader() {
        FinalDemo demo = finalDemo; // 3.读对象引用
        int a = demo.a;    //4.读普通域
        int b = demo.b;    //5.读final域
    }
}
```
假设线程A在执行writer()方法，线程B执行reader()方法。

> **写final域重排序规则**

写final域的重排序规则:**禁止对final域的写重排序到构造函数之外**，这个规则的实现主要包含了两个方面：

- JMM禁止编译器把final域的写重排序到构造函数之外
- 编译器会在final域写之后，构造函数return之前，插入一个**StoreStore屏障**。
这个屏障可以禁止处理器把final域的写重排序到构造函数之外。
(参见 StoreStore Barriers的说明：在Store1;Store2之间插入StoreStore，确保Store1对
其他处理器可见(刷新内存)先于Store2及所有后续存储指令的存储)

writer方法中，实际上做了两件事：

- 构造了一个FinalDemo对象
- 把这个对象赋值给成员变量finalDemo

可能的执行时序图如下：

<div align="center"> <img src="pics//06_00.png" width="400"/> </div><br>

a,b之间没有数据依赖性，普通域（普通变量）a可能会被**重排序到构造函数之外**，
线程B就有可能读到的是普通变量a初始化之前的值（零值），这样就可能出现错误。

final域变量b，根据重排序规则，会**禁止final修饰的变量b重排序到构造函数之外**，从而b能够正确赋值，
线程B就能够读到final变量初始化后的值。

因此，写final域的重排序规则可以确保：**在对象引用为任意线程可见之前，对象的final域已经被正确初始化过了**。
普通域不具有这个保障，比如在上例，线程B有可能就是一个未正确初始化的对象finalDemo。

> **读final域重排序规则**

读final域重排序规则:**在一个线程中，初次读对象引用和初次读该对象包含的final域，JMM会禁止这两个操作的重排序**。
（注意，这个规则仅仅是针对处理器），
处理器会在读final域操作的前面插入一个**LoadLoad屏障**。
实际上，读对象的引用和读该对象的final域存在间接依赖性，一般处理器不会重排序这两个操作。
但是有一些处理器会重排序，因此，这条禁止重排序规则就是针对这些处理器而设定的。

read方法主要包含了三个操作：

- 初次读引用变量finalDemo
- 初次读引用变量finalDemo的普通域a
- 初次读引用变量finalDemo的final域b

假设线程A写过程没有重排序，那么线程A和线程B有一种的可能执行时序如下：

<div align="center"> <img src="pics//06_01.png" width="400"/> </div><br>

读对象的普通域被重排序到了读对象引用的前面就会出现线程B还未读到对象引用就在读取该对象的普通域变量，这显然是错误的操作。

final域的读操作就“限定”了在读final域变量前已经读到了该对象的引用，从而就可以避免这种情况。

因此，读final域的重排序规则可以确保：**在读一个对象的final域之前，一定会先读这个包含这个final域的对象的引用**。

### final为引用类型
```java
public class FinalReferenceDemo {
    final int[] arrays; //arrays是引用类型
    private FinalReferenceDemo finalReferenceDemo;

    public FinalReferenceDemo() {
        arrays = new int[1];  //1 
        arrays[0] = 1;        //2
    }

    public void writerOne() {
        finalReferenceDemo = new FinalReferenceDemo(); //3
    }

    public void writerTwo() {
        arrays[0] = 2;  //4
    }

    public void reader() {
        if (finalReferenceDemo != null) {  //5
            int temp = finalReferenceDemo.arrays[0];  //6
        }
    }
}
```
> **对final修饰的对象的成员域进行写操作**

针对引用数据类型，final域写针对编译器和处理器重排序增加了这样的约束：
在**构造函数内对一个final修饰的对象的成员域的写入**，与随后**在构造函数之外**把这个被构造的对象的引用赋给一个引用变量，这两个操作是**不能被重排**序的。
注意这里的是“增加”也就说前面对final基本数据类型的重排序规则在这里还是使用。

线程线程A执行wirterOne方法，执行完后线程B执行writerTwo方法，线程C执行reader方法。
下图就以这种执行时序出现的一种情况来讨论：

<div align="center"> <img src="pics//06_02.png" width="400"/> </div><br>

对final域的写禁止重排序到构造方法外，因此1和3不能被重排序。
由于一个final域的引用对象的成员域写入不能与在构造函数之外将这个被构造出来的对象赋给引用变量重排序，
因此2和3不能重排序。

> **对final修饰的对象的成员域进行读操作**

JMM可以确保线程C至少能看到写线程A对final引用的对象的成员域的写入，即能看到arrays[0] = 1，而
写线程B对数组元素的写入可能看到可能看不到。
JMM不保证线程B的写入对线程C可见，线程B和线程C之间存在数据竞争，此时的结果是不可预知的。
如果想要可见，可使用锁或者volatile。

> **final重排序的总结**

|  | final写  |  final域读  | 
| :---: | :---: | :---:|
| 基本数据类型 | 禁止final域写与构造方法重排序，即禁止final域写重排序到构造方法之外，从而保证该对象对所有线程可见时，该对象的final域全部已经初始化过 | 禁止初次读对象的引用与读该对象包含的final域的重排序，保证了在读一个对象的final域之前，一定会先读这个包含这个final域的对象的引用 |
| 引用数据类型 | 额外增加约束：**构造函数内对一个final修饰的对象的成员域的写入**，与随后**在构造函数之外**把这个被构造的对象的引用赋给一个引用变量，这两个操作是**不能被重排**序的 | |

## 对象溢出
对象溢出：一种错误的发布，当一个对象还没有构造完成时，就使它被其他线程所见。

```java
/**
* 对象溢出示例
*/
public class ThisEscape {
　　public ThisEscape(EventSource source) {
　　　　source.registerListener(new EventListener() {
　　　　　　public void onEvent(Event e) {
　　　　　　　　doSomething(e);
　　　　　　}
　　　　});
　　}
 
　　void doSomething(Event e) {
　　}
}
```
这将导致this逸出，所谓逸出，就是在不该发布的时候发布了一个引用。

在这个例子里面，当我们实例化ThisEscape对象时，会调用source的registerListener方法，
这时便启动了一个线程，而且这个线程持有了ThisEscape对象（调用了对象的doSomething方法），
但此时ThisEscape对象却没有实例化完成（还没有返回一个引用），所以我们说，
此时造成了一个this引用逸出，即还没有完成的实例化ThisEscape对象的动作，却已经暴露了对象的引用。

正确构造过程：
```java
public class SafeListener {
　　private final EventListener listener;
 
　　private SafeListener() {
　　　　listener = new EventListener() {
　　　　　　public void onEvent(Event e) {
　　　　　　　　doSomething(e);
　　　　　　}
　　　　};
　　}
 
　　public static SafeListener newInstance(EventSource source) {
　　　　SafeListener safe = new SafeListener();
　　　　source.registerListener(safe.listener);
　　　　return safe;
　　}
 
　　void doSomething(Event e) {
　　}
```
当构造好了SafeListener对象（通过构造器构造）之后，
我们才启动了监听线程，也就确保了SafeListener对象是构造完成之后再使用的SafeListener对象。

结论：

- 只有当构造函数返回时，this引用才应该从线程中逸出。
- 构造函数可以将this引用保存到某个地方，只要其他线程不会在构造函数完成之前使用它。

## final的实现原理
写final域会要求编译器在final域写之后，构造函数返回前插入一个StoreStore屏障。

读final域的重排序规则会要求编译器在读final域的操作前插入一个LoadLoad屏障。

如果以X86处理为例，**X86不会对写-写重排序**，所以StoreStore屏障可以省略。
由于**不会对有间接依赖性的操作重排序**，所以在X86处理器中，读final域需要的LoadLoad屏障也会被省略掉。
也就是说，以X86为例的话，对final域的读/写的内存屏障都会被省略！具体是否插入还是得看是什么处理器。

- 注意：

上面对final域写重排序规则可以确保我们在使用一个对象引用的时候该对象的final域已经在构造函数被初始化过了。
但是这里其实是有一个前提条件：
**在构造函数，不能让这个被构造的对象被其他线程可见，也就是说该对象引用不能在构造函数中“溢出”**。

```java
public class FinalReferenceEscapeDemo {
    private final int a;
    private FinalReferenceEscapeDemo referenceDemo;

    public FinalReferenceEscapeDemo() {
        a = 1;  //1
        referenceDemo = this; //2
    }

    public void writer() {
        new FinalReferenceEscapeDemo();
    }

    public void reader() {
        if (referenceDemo != null) {  //3
            int temp = referenceDemo.a; //4
        }
    }
}
```
<div align="center"> <img src="pics//06_03.png" width="400"/> </div><br>

假设一个线程A执行writer方法另一个线程执行reader方法。因为构造函数中操作1和2之间没有数据依赖性，1和2可以重排序，先执行了2，这个时候引用对象referenceDemo是个没有完全初始化的对象，而当线程B去读取该对象时就会出错。尽管依然满足了final域写重排序规则：在引用对象对所有线程可见时，其final域已经完全初始化成功。
但是，引用对象“this”逸出，该代码依然存在线程安全的问题。