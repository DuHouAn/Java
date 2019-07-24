package code_04_keyWords;

/**
 * 静态方法：
 * 只能访问所属类的静态字段和静态方法，方法中不能有 this 和 super 关键字。
 */
public class StaticEaxmple2 {
    private static int x; //静态变量
    private int y;//实例变量

    public static void func1(){
        int a = x;
        // int b = y;  // Non-static field 'y' cannot be referenced from a static context
        // int b = this.y;     // 'A.this' cannot be referenced from a static context
    }
}
