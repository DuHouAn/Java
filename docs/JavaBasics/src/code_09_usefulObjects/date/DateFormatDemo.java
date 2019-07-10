package code_09_usefulObjects.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 18351 on 2018/12/25.
 */
public class DateFormatDemo {
    public static void main(String[] args) {
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
        String s=dateToString(date,sdf);
        System.out.println(s); //2018年12月25日
        System.out.println(stringToDate(s,sdf));//Tue Dec 25 00:00:00 GMT+08:00 2018
    }

    /**
     * Date	 --	 String(格式化)
     * 		public final String format(Date date)
     */
    public static String dateToString(Date d, SimpleDateFormat sdf) {
        return sdf.format(d);
    }

    /**
     * * String -- Date(解析)
     * 		public Date parse(String source)
     */
    public static Date stringToDate(String s, SimpleDateFormat sdf){
        Date date=null;
        try {
            date=sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
