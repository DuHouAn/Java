package code_09_usefulObjects.integer;

/**
 * JDK5的新特性
 * 自动装箱：把基本类型转换为包装类类型
 * 自动拆箱：把包装类类型转换为基本类型
 *
 * 注意一个小问题：
 * 		在使用时，Integer  x = null;代码就会出现NullPointerException。
 * 		建议先判断是否为null，然后再使用。
 */
public class IntegerDemo4 {
    public static void main(String[] args) {
        Integer num=null;
        if(num!=null){
            num+=100;
            System.out.println(num);
        }
        test(); //num:200
        test2(); //num:200
    }

    //自动装箱,拆箱
    public static void test() {
        Integer num=100;
        //自动装箱，实际上就是 Integer num=Integer.valueOf(100)
        num+=100;
        //先拆箱再装箱  num.intValue()+100=200 --> Integer num=Integer.valueOf(num.intValue()+100)
        System.out.println(new StringBuilder("num:").append(num).toString());
    }

    //手动拆装箱
    public static void test2() {
        Integer num=Integer.valueOf(100);
        num=Integer.valueOf(num.intValue()+100);
        System.out.println(new StringBuilder("num:").append(num).toString());
    }
}
