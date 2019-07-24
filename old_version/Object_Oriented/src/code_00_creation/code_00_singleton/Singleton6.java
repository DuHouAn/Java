package code_00_creation.code_00_singleton;

/**
 * Created by 18351 on 2018/12/27.
 */
public class Singleton6 {
    private Singleton6(){

    }

    public static Singleton6 getUniqueInstance(){
        return Singleton.INSTANCE.getSingleton();
    }

    private enum Singleton{
        INSTANCE;
        //如果打算自定义自己的方法，那么必须在enum实例序列的最后添加一个分号。
        //而且 Java 要求必须先定义 enum 实例
        private Singleton6 singleton;

        //JVM保证这个方法绝对只被调用一次
        Singleton(){
            singleton=new Singleton6();
        }

        public Singleton6 getSingleton() {
            return singleton;
        }
    }
}
