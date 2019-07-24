package code_09_usefulObjects.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 18351 on 2018/12/25.
 */
public class DateFormatDemo2 {
    public static void main(String[] args) {
        Date d = new Date();
        // yyyy-MM-dd HH:mm:ss
        String s = DateUtil.dateToString(d, "yyyy年MM月dd日 HH:mm:ss");
        System.out.println(s);//2018年12月25日 20:24:16

        String s2 = DateUtil.dateToString(d, "yyyy年MM月dd日");
        System.out.println(s2);//2018年12月25日

        String s3 = DateUtil.dateToString(d, "HH:mm:ss");
        System.out.println(s3);//20:24:16

        String str = "2014-10-14";
        Date dd = DateUtil.stringToDate(str, "yyyy-MM-dd");
        System.out.println(dd);//Tue Oct 14 00:00:00 GMT+08:00 2014
    }
}
