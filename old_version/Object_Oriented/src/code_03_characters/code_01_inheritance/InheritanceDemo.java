package code_03_characters.code_01_inheritance;

/**
 * 1. **子类中所有的构造方法默认会都会访问父类中空参数的构造方法**

 原因：因为子类会继承父类中的数据，可能还会使用父类的数据。所以，子类初始化之前，
 一定要完成父类数据的初始化。

 2. 每一个子类的构造方法第一条语句默认是：super();

 3. 如果父类中没有无参构造方法，该怎么办呢？

 方式一：子类通过super去显示调用父类其他的带参的构造方法

 方式二：子类通过this去调用本类的其他构造方法
 （子类一定要有一个去访问父类的构造方法，否则父类数据就没有初始化）
 */
class Father{
    public Father() {
           System.out.println("Father的无参构造函数");
    }
}

class Son extends Father{
    public Son() {
        System.out.println("Son的无参构造函数");
    }
    public Son(String name) {
        System.out.println("Son的带参构造函数");
    }
}

public class InheritanceDemo {
    public static void main(String[] args) {
        //使用无参构造函数初始化
        Son son=new Son();
        System.out.println("===========");
        //使用带参构造函数初始化
        Son son2=new Son("林青霞");
    }
}
