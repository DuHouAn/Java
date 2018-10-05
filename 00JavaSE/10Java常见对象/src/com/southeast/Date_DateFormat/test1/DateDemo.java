package com.southeast.Date_DateFormat.test1;

import java.util.Date;

/**
 *  Date:表示特定的瞬间，精确到毫秒。
 *
 * 构造方法：
 * 		Date():根据当前的默认毫秒值创建日期对象
 * 		Date(long date)：根据给定的毫秒值创建日期对象
 */
public class DateDemo {
    public static void main(String[] args) {
        // Date():根据当前的默认毫秒值创建日期对象
        Date d = new Date();
        System.out.println("d:" + d);

        // Date(long date)：根据给定的毫秒值创建日期对象
        //long time = System.currentTimeMillis();
        long time = 1000 * 60 * 60; // 1小时
        Date d2 = new Date(time);
        System.out.println("d2:" + d2);
        //格林威治时间    1970年01月01日00时00分00
        //Thu Jan 01 09:00:00 GMT+08:00 1970  GMT+表示 标准时间加8小时，因为中国是东八区
    }
}
