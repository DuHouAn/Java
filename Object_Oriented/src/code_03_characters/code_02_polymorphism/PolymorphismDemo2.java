package code_03_characters.code_02_polymorphism;

/**
 * 多态中的成员访问特点：
     A:成员变量
        编译看左边，运行看左边。
    B:构造方法
         创建子类对象的时候，访问父类的构造方法，对父类的数据进行初始化。
    C:成员方法
        编译看左边，运行看右边。( 由于成员方法存在方法重写，所以它运行看右边。)
    D:静态方法
        编译看左边，运行看左边。(静态和类相关，算不上重写，所以，访问还是左边的)
 */
class Fu{
    public int num=100;

    public void show(){
        System.out.println("show function");
    }

    public static void function(){
        System.out.println("function Fu");
    }
}

class Zi extends Fu {
    public int num = 1000;
    public int num2 = 200;

    @Override
    public void show() {
        System.out.println("show Zi");
    }

    public void method() {
        System.out.println("method zi");
    }

    public static void function() {
        System.out.println("function Zi");
    }
}

public class PolymorphismDemo2 {
    public static void main(String[] args) {
        Fu f = new Zi();
        System.out.println(f.num);

        //System.out.println(f.num2);
        f.show();
        //找不到符号
        //f.method();
        f.function();
    }
}
