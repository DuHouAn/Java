package code_03_characters.code_01_inheritance;

 /**
 * 一个类的成员变量的初始化：
 *      默认初始化
 *      显式初始化
 *      构造方法初始化
 * 子类的初始化（分层初始化）
 *      先进行父类的初始化，然后再进行子类的初始化
 */

class X{
    Y b=new Y(); //显式初始化

    X(){
        System.out.println("X");
    }
}

class Y{
    Y(){
        System.out.println("Y");
    }
}

public class Z extends X{
    Y y=new Y();

    public Z() {
        //super();//子类的初始化（分层初始化）:先进行父类的初始化，然后再进行子类的初始化。
        System.out.println("Z");
    }

    public static void main(String[] args) {
        new Z();
    }
}
