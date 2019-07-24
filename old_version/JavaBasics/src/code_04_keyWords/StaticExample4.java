package code_04_keyWords;

/**
 * 静态变量和静态语句块优先于实例变量和普通语句块，
 * 静态变量和静态语句块的初始化顺序取决于它们在代码中的顺序。
 */
public class StaticExample4 {
    public static String staticField = "静态变量";

    static {
        System.out.println("静态语句块");
    }

    public String field = "实例变量";
    {
        System.out.println("普通语句块");
    }

    public StaticExample4(){
        System.out.println("构造函数");
    }

    public static void main(String[] args) {
        StaticExample4 e=new StaticExample4();
    }
}
