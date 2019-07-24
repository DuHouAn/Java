package code_06_exception.catchException.tryCatch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 编译时异常和运行时异常的区别：
 * （1）编译期异常：java程序必须显示处理，否则在编译器就会报错。
 * （2）运行期异常：编程足够严谨，就能避免，可以不处理。
 */
public class Code_05_Exception {
    public static void main(String[] args) {
        //运行期异常：编程足够严谨，就能避免，可以不处理。
        // int a = 10;
        // int b = 0;
        // if (b != 0) {
        // System.out.println(a / b);
        // }

        //编译期异常：java程序必须显示处理，否则在编译器就会报错。
        String s = "2014-11-20";
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(s);
        } catch (ParseException e) {
            // e.printStackTrace();
            System.out.println("解析日期出问题了");
        }
        System.out.println(d);
    }
}
