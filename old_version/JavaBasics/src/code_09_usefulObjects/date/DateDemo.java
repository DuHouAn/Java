package code_09_usefulObjects.date;

import java.util.Date;

/**
 * 把一个毫秒值转换为Date，有两种方式：
 * (1)构造方法
 * (2)setTime(long time)
 */
public class DateDemo {
    public static void main(String[] args) {
        // Date():根据当前的默认毫秒值创建日期对象
        Date d = new Date();
        System.out.println("d:" + d);
        //d:Tue Dec 25 20:01:17 GMT+08:00 2018 --> 当前时间

        // Date(long date)：根据给定的毫秒值创建日期对象
        //long time = System.currentTimeMillis();
        long time = 1000 * 60 * 60; // 1小时
        Date d2 = new Date(time);
        System.out.println("d2:" + d2);
        //格林威治时间    1970年01月01日00时00分00
        //Thu Jan 01 09:00:00 GMT+08:00 1970  GMT+表示 标准时间加8小时，因为中国是东八区

        // 获取时间
        long time2 = d.getTime();
        System.out.println(time2); //1545739438466 毫秒
        System.out.println(System.currentTimeMillis());

        // 设置时间
        d.setTime(1000*60*60);
        System.out.println("d:" + d);
        //Thu Jan 01 09:00:00 GMT+08:00 1970
    }
}