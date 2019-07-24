package code_04_keyWords;

/**
 * 静态变量：
 * 又称为类变量，也就是说这个变量属于类的，**类所有的实例都共享静态变量**，
 * 可以直接通过类名来访问它。静态变量在内存中只存在一份。
 *
 * 实例变量：
 * 每创建一个实例就会产生一个实例变量，它与该实例同生共死。
 */
public class StaticEaxmple {
    private int x;         // 实例变量
    private static int y;  // 静态变量

    public static void main(String[] args) {
        // int x = A.x;  // Non-static field 'x' cannot be referenced from a static context
        StaticEaxmple staticEaxmple=new StaticEaxmple ();
        int x = staticEaxmple.x;
        int y = StaticEaxmple.y;
    }
}
