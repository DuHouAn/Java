package code_09_usefulObjects.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 18351 on 2018/12/25.
 */
public class DateUtil {
    /**
     * Date	 --	 String(格式化)
     * 		public final String format(Date date)
     */
    public static String dateToString(Date d,String pattern) {
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        return sdf.format(d);
    }

    /**
     * * String -- Date(解析)
     * 		public Date parse(String source)
     */
    public static Date stringToDate(String s, String pattern){
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        Date date=null;
        try {
            date=sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
