package code_00_creation.code_00_singleton;

/**
 * Created by 18351 on 2018/12/27.
 */
public class SingletonTest {
    public static void main(String[] args) {
        Singleton s1=Singleton.getUniquueInstance();
        Singleton s2=Singleton.getUniquueInstance();
        System.out.println(s1==s2);

        Singleton2 s3=Singleton2.getUniqueInstance();
        Singleton2 s4=Singleton2.getUniqueInstance();
        System.out.println(s3==s4);

        Singleton3 s5=Singleton3.getUniqueInstance();
        Singleton3 s6=Singleton3.getUniqueInstance();
        System.out.println(s5==s6);

        Singleton4 s7=Singleton4.getUniqueInstance();
        Singleton4 s8=Singleton4.getUniqueInstance();
        System.out.println(s7==s8);

        Singleton5 s9=Singleton5.getUniqueInstance();
        Singleton5 s10=Singleton5.getUniqueInstance();
        System.out.println(s7==s8);


        Singleton6 s11=Singleton6.getUniqueInstance();
        Singleton6 s12=Singleton6.getUniqueInstance();
        System.out.println(s9==s10);
    }
}