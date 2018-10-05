package com.southeast.Date_DateFormat.test4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 这是一个工具类
 * 将 Date和String相互转化
 */
public class DateUtil {
    /**
     * Date	 --	 String(格式化)
     * 		public final String format(Date date)
     */
    public static String dateToString(Date d,String sdfStr){
        SimpleDateFormat sdf=new SimpleDateFormat(sdfStr);
        return sdf.format(d);
    }

    /**
     * * String -- Date(解析)
     * 		public Date parse(String source)
     */
    public static Date stringToDate(String s,String sdfStr) {
        SimpleDateFormat sdf=new SimpleDateFormat(sdfStr);
        Date d=null;
        try {
            d=sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
}
