package code_00_dataType;

/**
 * Created by 18351 on 2018/12/20.
 */
public class DataTypeDemo {
    public static void main(String[] args) {
        Integer x = new Integer(123);
        Integer y = new Integer(123);
        System.out.println(x == y);    // false
        Integer z = Integer.valueOf(123);
        Integer k = Integer.valueOf(123);
        System.out.println(z == k);   // true
    }
}
