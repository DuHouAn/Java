package code_03_characters.code_00_encapsulation;

/**
 * Created by 18351 on 2019/1/14.
 */
public class CodeBlock {
    //构造方法
    CodeBlock(){
        int a=10;
        System.out.println(a);
    }

    //构造代码块
    {
        int a=100;
        System.out.println(a);
    }

    //静态代码块
    static {
        int a=1000;
        System.out.println(a);
    }

    public static void main(String[] args) {
        CodeBlock codeBlock=new CodeBlock();
    }
}
