/**
 * Created by 18351 on 2018/12/14.
 */
public class FinalExample {
    //声明变量的时候，就进行初始化
    private final int num=6;
    //类变量必须要在静态初始化块中指定初始值或者声明该类变量时指定初始值
    // private final String str; //编译错误：因为非静态变量不可以在静态初始化快中赋初值
    private final static String name;
    private final double score;
    private final char ch;
    //private final char ch2;//编译错误:TODO：因为没有在构造器、初始化代码块和声明时赋值

    {
        //实例变量在初始化代码块赋初值
        ch='a';
    }

    static {
        name="aaaaa";
    }

    public FinalExample(){
        //num=1;编译错误：已经赋值后，就不能再修改了
        score=90.0;
    }

    public void ch2(){
        //ch2='c';//编译错误：实例方法无法给final变量赋值
    }

    public void test(){
        final int a=1;
        //a=2;//编译错误：final局部变量已经进行了初始化则后面就不能再次进行更改
    }
}