package com.southeast.Date_DateFormat.test3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 ** Date	 --	 String(格式化)
 * 		public final String format(Date date)
 *
 * String -- Date(解析)
 * 		public Date parse(String source)
 *
 * DateForamt:可以进行日期和字符串的格式化和解析，但是由于是抽象类，所以使用具体子类SimpleDateFormat。
 *
 * SimpleDateFormat的构造方法：
 * 		SimpleDateFormat():默认模式
 * 		SimpleDateFormat(String pattern):给定的模式
 * 			这个模式字符串该如何写呢?
 * 			通过查看API，我们就找到了对应的模式
 * 			年 y
 * 			月 M
 * 			日 d
 * 			时 H
 * 			分 m
 * 			秒 s
 *
 * 			2014年12月12日 12:12:12
 */
public class DateFormatDemo {
    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");

        // 创建日期对象
        Date d = new Date();

        String s=dateToString(d,sdf);
        System.out.println(s);
        System.out.println(stringToDate(s,sdf));
    }


    /**
     * Date	 --	 String(格式化)
     * 		public final String format(Date date)
     */
    public static String dateToString(Date d,SimpleDateFormat sdf){
        return sdf.format(d);
    }

    /**
     * * String -- Date(解析)
     * 		public Date parse(String source)
     */
    public static Date stringToDate(String s,SimpleDateFormat sdf) {
        Date d=null;
        try {
            d=sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

}
