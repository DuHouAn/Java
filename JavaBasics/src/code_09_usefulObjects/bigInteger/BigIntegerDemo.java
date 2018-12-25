package code_09_usefulObjects.bigInteger;

import java.math.BigInteger;

/**
 * Created by 18351 on 2018/12/25.
 */
public class BigIntegerDemo {
    public static void main(String[] args) {
        Integer num = new Integer("2147483647");
        System.out.println(num);

        //Integer num2 = new Integer("2147483648");
        // Exception in thread "main" java.lang.NumberFormatException: For input string: "2147483648"
        //System.out.println(num2);

        // 通过 BigIntege来创建对象
        BigInteger num2 = new BigInteger("2147483648");
        System.out.println(num2);
    }
}
