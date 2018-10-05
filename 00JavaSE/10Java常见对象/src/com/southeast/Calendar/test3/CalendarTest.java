package com.southeast.Calendar.test3;

import java.util.Calendar;
import java.util.Scanner;

/**
 *获取任意一年的二月有多少天
 *
 * 分析：
 * 		A:键盘录入任意的年份
 * 		B:设置日历对象的年月日
 * 			年就是A输入的数据
 * 			月是2
 * 			日是1
 * 		C:把时间往前推一天，就是2月的最后一天
 * 		D:获取这一天输出即可
 */
public class CalendarTest {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int year=sc.nextInt();

        Calendar c=Calendar.getInstance();
        c.set(year,2,1); //得到的就是该年的3月1日
        c.add(Calendar.DATE,-1);//把时间往前推一天，就是2月的最后一天
        //public void add(int field,int amount):根据给定的日历字段和对应的时间，来对当前的日历进行操作。

        System.out.println(year+"年，二月有"+c.get(Calendar.DATE)+"天");
    }
}
