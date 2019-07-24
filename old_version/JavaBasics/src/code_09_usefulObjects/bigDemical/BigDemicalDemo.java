package code_09_usefulObjects.bigDemical;

import java.math.BigDecimal;

/**
 * Created by 18351 on 2018/12/25.
 */
public class BigDemicalDemo {
    public static void main(String[] args) {
        /*System.out.println(0.09 + 0.01);
        System.out.println(1.0 - 0.32);
        System.out.println(1.015 * 100);
        System.out.println(1.301 / 100);
        System.out.println(1.0 - 0.12);*/

        BigDecimal bd1 = new BigDecimal("0.09");
        BigDecimal bd2 = new BigDecimal("0.01");
        System.out.println("add:" + bd1.add(bd2));//add:0.10
        System.out.println("-------------------");

        BigDecimal bd3 = new BigDecimal("1.0");
        BigDecimal bd4 = new BigDecimal("0.32");
        System.out.println("subtract:" + bd3.subtract(bd4));//subtract:0.68
        System.out.println("-------------------");

        BigDecimal bd5 = new BigDecimal("1.015");
        BigDecimal bd6 = new BigDecimal("100");
        System.out.println("multiply:" + bd5.multiply(bd6));//multiply:101.500
        System.out.println("-------------------");

        BigDecimal bd7 = new BigDecimal("1.301");
        BigDecimal bd8 = new BigDecimal("100");
        System.out.println("divide:" + bd7.divide(bd8));//divide:0.01301

        //四舍五入
        System.out.println("divide:"
                + bd7.divide(bd8, 3, BigDecimal.ROUND_HALF_UP));//保留三位有效数字
        //divide:0.013

        System.out.println("divide:"
                + bd7.divide(bd8, 8, BigDecimal.ROUND_HALF_UP));//保留八位有效数字
        //divide:0.01301000
    }
}
