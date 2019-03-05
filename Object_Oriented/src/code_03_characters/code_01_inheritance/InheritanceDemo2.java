package code_03_characters.code_01_inheritance;

/**
 * 3. 如果父类中没有无参构造方法，该怎么办呢？

 方式一：子类通过super去显示调用父类其他的带参的构造方法

 方式二：子类通过this去调用本类的其他构造方法
 （子类一定要有一个去访问父类的构造方法，否则父类数据就没有初始化）
 */
class Parent{
    public Parent(String name){
        System.out.println("Parent的带参构造函数");
    }
}

class Child extends Parent{
    public Child() {
        //子类通过super去显示调用父类其他的带参的构造方法
        super("林青霞");
        System.out.println("Child的无参构造函数");
    }
    public Child(String name) {
        //方式一：子类通过super去显示调用父类其他的带参的构造方法
        //super(name);
        //方式二：子类通过this去调用本类的其他构造方法
        this();//嗲用Child()的无参构造函数
        System.out.println("Child的带参构造函数");
    }
}

public class InheritanceDemo2 {
    public static void main(String[] args) {
        Child c = new Child();
        System.out.println("==============");
        Child c2 = new Child("林青霞");
    }
}
