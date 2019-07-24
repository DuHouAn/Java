package code_08_annotation.code_00;

/**
 * Created by 18351 on 2018/12/25.
 */
public class AppleTest {
    public static void main(String[] args) {
        //方式一:
        Apple apple=new Apple();
        FruitInfoUtil.getFruitInfo(apple.getClass());
        //直接通过反射得到注解中的值

        //这种访问 是获取不了注解中的值的
        System.out.println(apple.getAppleColor());//null
        System.out.println(apple.getAppleName());//null
        System.out.println(apple.getAppleProvider());//null

        //方式二：
        FruitInfoUtil.getFruitInfo(Apple.class);
    }
}
