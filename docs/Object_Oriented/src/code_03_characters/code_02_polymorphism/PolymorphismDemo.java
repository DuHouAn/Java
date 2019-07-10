package code_03_characters.code_02_polymorphism;

/**
 * 对象间的转型问题：
 向上转型：
 Parent f = new Child();
 向下转型：
 Child z = (Child)f; //要求该f必须是能够转换为Zi的。(父到子)
 */
class Parent {
    public void show() {
        System.out.println("show fu");
    }
}

class Child extends Parent {
    @Override
    public void show() {
        System.out.println("show zi");
    }

    public void method() {
        System.out.println("method zi");
    }

}

public class PolymorphismDemo {
    public static void main(String[] args) {
        Parent fu=new Child();
        fu.show();
        //fu.method();//不能访问子类特有功能（特有方法）

        Child zi=(Child)fu; //向下转型
        zi.show();
        zi.method();
    }
}
