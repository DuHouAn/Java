package code_04_keyWords;

/**
 * 静态语句块
 * 静态语句块在类初始化时运行一次。
 */
public class StaticEaxmple3 {
    static {
        System.out.println("123");
    }

    public static void main(String[] args) {
        StaticEaxmple3 a1 = new StaticEaxmple3();
        StaticEaxmple3 a2 = new StaticEaxmple3();
    }
}
