package code_04_keyWords;

/**
 * 静态内部类
 * 非静态内部类依赖于外部类的实例，而静态内部类不需要。
 *
 * 注意：静态内部类不能访问外部类的非静态的变量和方法。
 */
public class OuterClass {
    private int x; //实例变量

    //非静态内部类
    class InnerClass {
    }

    //静态内部类
    static class StaticInnerClass {
        public StaticInnerClass(){
            //System.out.println(x);//静态内部类不能访问外部类的非静态的变量和方法
        }
    }

    public static void main(String[] args) {
        // InnerClass innerClass = new InnerClass(); // 'OuterClass.this' cannot be referenced from a static context
        OuterClass outerClass = new OuterClass();
        InnerClass innerClass = outerClass.new InnerClass();
        StaticInnerClass staticInnerClass = new StaticInnerClass();
    }
}
