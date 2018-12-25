package code_09_usefulObjects.bigInteger;

import java.math.BigInteger;

/**
 * Created by 18351 on 2018/12/25.
 */
public class BigIntegerDemo2 {
    public static void main(String[] args) {
        BigInteger bi1 = new BigInteger("100");
        BigInteger bi2 = new BigInteger("50");

        // public BigInteger add(BigInteger val):加
        System.out.println("add:" + bi1.add(bi2)); //add:150
        // public BigInteger subtract(BigInteger Val):减
        System.out.println("subtract:" + bi1.subtract(bi2));//subtract:50
        // public BigInteger multiply(BigInteger val):乘
        System.out.println("multiply:" + bi1.multiply(bi2));//multiply:5000
        // public BigInteger divide(BigInteger val):除
        System.out.println("divide:" + bi1.divide(bi2));//divide:2

        // public BigInteger[] divideAndRemainder(BigInteger val):返回商和余数的数组
        BigInteger[] bis = bi1.divideAndRemainder(bi2);
        System.out.println("divide：" + bis[0]);//divide：2
        System.out.println("remainder：" + bis[1]);//remainder：0
    }
}
