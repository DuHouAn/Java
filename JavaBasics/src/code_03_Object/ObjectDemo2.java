package code_03_Object;

/**
 * - 对于基本类型:
 * == 判断两个值是否相等，基本类型没有 equals() 方法。
 *
 * - 对于引用类型，
 * == 判断两个变量是否引用同一个对象，而 equals() 判断引用的对象是否等价。
 */
public class ObjectDemo2 {
    public static void main(String[] args) {
        EqualExample x = new EqualExample(1,2,3);
        EqualExample y = new EqualExample(1,2,3);
        System.out.println(x.equals(y)); // true
        System.out.println(x == y);      // false
    }
}
