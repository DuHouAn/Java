<!-- GFM-TOC -->
* [十、Java常见对象](#十Java常见对象)
    * [Arrays](#Arrays)
    * [BigDemical](#BigDemical)
    * [BigInteger](#BigInteger)
    * [Calendar](#Calendar)
    * [Character](#Character)
    * [Data_DateFormat](#Data_DateFormat)
    * [Integer](#Integer)
    * [Object](#Object)
    * [Scanner](#Scanner)
    * [String](#String)
    * [StringBuffer](#StringBuffer)
<!-- GFM-TOC -->
# 十、Java常见对象
## Arrays
Arrays:针对数组进行操作的工具类。

- Arrays的常用成员方法：
```java
public static String toString(int[] a) //把数组转成字符串

public static void sort(int[] a) //对数组进行排序

public static int binarySearch(int[] a,int key) //二分查找
```

- toString()源码如下：
```java
public static String toString(int[] a) {
         if (a == null)
            return "null";
         int iMax = a.length - 1;
         if (iMax == -1)
            return "[]";

         StringBuilder b = new StringBuilder();
         b.append('[');
         for (int i = 0; ; i++) {
            b.append(a[i]);
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
        }
     }
```
- binarySearch()调用的是binarySearch0(),源码如下：
```java
 private static int binarySearch0(int[] a, int fromIndex, int toIndex,int key) {
     int low = fromIndex;
     int high = toIndex - 1;

     while (low <= high) {
     int mid = (low + high) >>> 1;
     int midVal = a[mid];

     if (midVal < key)
            low = mid + 1;
     else if (midVal > key)
            high = mid - 1;
     else
            return mid; // key found
     }
     return -(low + 1);  // key not found.
 }
```

- 使用示例：
```java
public class ArraysDemo {
    public static void main(String[] args) {
        // 定义一个数组
        int[] arr = { 24, 69, 80, 57, 13 };

        // public static String toString(int[] a) 把数组转成字符串
        System.out.println("排序前：" + Arrays.toString(arr));//排序前：[24, 69, 80, 57, 13]

        // public static void sort(int[] a) 对数组进行排序
        Arrays.sort(arr);
        System.out.println("排序后：" + Arrays.toString(arr));//排序后：[13, 24, 57, 69, 80]

        // [13, 24, 57, 69, 80]
        // public static int binarySearch(int[] a,int key) 二分查找
        System.out.println("binarySearch:" + Arrays.binarySearch(arr, 57));//binarySearch:2
        System.out.println("binarySearch:" + Arrays.binarySearch(arr, 577));//binarySearch:-6
    }
}
```
## BigDemical
BigDecimal类：不可变的、任意精度的有符号十进制数,可以解决数据丢失问题。

看如下程序，写出结果
```java
public static void main(String[] args) {
    System.out.println(0.09 + 0.01);
    System.out.println(1.0 - 0.32);
    System.out.println(1.015 * 100);
    System.out.println(1.301 / 100);
    System.out.println(1.0 - 0.12);
}
```
输出结果
```html
0.09999999999999999
0.6799999999999999
101.49999999999999
0.013009999999999999
0.88
```
结果和我们想的有一点点不一样，这是因为浮点数类型的数据存储和整数不一样导致的。
它们大部分的时候，都是带有有效数字位。由于在运算的时候，float类型和double很容易丢失精度，
所以，为了能精确的表示、计算浮点数，Java提供了BigDecimal。

BigDecimal的常用成员方法：
```java
public BigDecimal(String val) //构造方法

public BigDecimal add(BigDecimal augend) //加
 
 public BigDecimal subtract(BigDecimal subtrahend)//减
 
 public BigDecimal multiply(BigDecimal multiplicand) //乘
 
 public BigDecimal divide(BigDecimal divisor) //除
 
 public BigDecimal divide(BigDecimal divisor,int scale,int roundingMode)//除法，scale：几位小数，roundingMode：如何舍取
```

- 使用BigDecimal改进
```java
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
```

## BigInteger
BigInteger:可以让超过Integer范围内的数据进行运算

```java
public static void main(String[] args) {
    Integer num = new Integer("2147483647");
    System.out.println(num);

    //Integer num2 = new Integer("2147483648");
    // Exception in thread "main" java.lang.NumberFormatException: For input string: "2147483648"
    //System.out.println(num2);

    // 通过 BigIntege来创建对象
    BigInteger num2 = new BigInteger("2147483648");
    System.out.println(num2);
}
```

- BigInteger的常用成员方法：
```java
public BigInteger add(BigInteger val) //加

public BigInteger subtract(BigInteger val) //减

public BigInteger multiply(BigInteger val) //乘

public BigInteger divide(BigInteger val) //除

public BigInteger[] divideAndRemainder(BigInteger val)//返回商和余数的数组
```
- 使用实例：
```java
public class BigIntegerDemo {
    public static void main(String[] args) {
        Integer num = new Integer("2147483647");
        System.out.println(num);

        //Integer num2 = new Integer("2147483648");
        // Exception in thread "main" java.lang.NumberFormatException: For input string: "2147483648"
        //System.out.println(num2);

        // 通过 BigIntege来创建对象
        BigInteger num2 = new BigInteger("2147483648");
        System.out.println(num2);
    }
}
```

```java
public class BigIntegerDemo2 {
    public static void main(String[] args) {
        BigInteger bi1 = new BigInteger("100");
        BigInteger bi2 = new BigInteger("50");

        // public BigInteger add(BigInteger val):加
        System.out.println("add:" + bi1.add(bi2)); //add:150
        // public BigInteger subtract(BigInteger Val):减
        System.out.println("subtract:" + bi1.subtract(bi2));//subtract:50
        // public BigInteger multiply(BigInteger val):乘
        System.out.println("multiply:" + bi1.multiply(bi2));//multiply:5000
        // public BigInteger divide(BigInteger val):除
        System.out.println("divide:" + bi1.divide(bi2));//divide:2

        // public BigInteger[] divideAndRemainder(BigInteger val):返回商和余数的数组
        BigInteger[] bis = bi1.divideAndRemainder(bi2);
        System.out.println("divide：" + bis[0]);//divide：2
        System.out.println("remainder：" + bis[1]);//remainder：0
    }
}
```
 
## Calendar
Calendar为特定瞬间与一组诸如 YEAR、MONTH、DAY_OF_MONTH、HOUR 等日历字段之间的转换提供了一些方法，
并为操作日历字段（例如获得下星期的日期）提供了一些方法。

Calendar中常用的方法：

```java
public int get(int field) //返回给定日历字段的值。日历类中的每个日历字段都是静态的成员变量，并且是int类型。

public void add(int field,int amount)//根据给定的日历字段和对应的时间，来对当前的日历进行操作。

public final void set(int year,int month,int date)//设置当前日历的年月日
```
- 使用示例：
```java
public class CalendarDemo {
    public static void main(String[] args) {
        // 其日历字段已由当前日期和时间初始化：
        Calendar rightNow = Calendar.getInstance(); // 子类对象
        int year=rightNow.get(Calendar.YEAR);
        int month=rightNow.get(Calendar.MONTH);//注意月份是从0开始的
        int date=rightNow.get(Calendar.DATE);
        System.out.println(year + "年" + (month + 1) + "月" + date + "日");
        //2018年12月25日
    }
}
```
- 使用示例2:
```java
public class CalendarDemo2 {
    public static void main(String[] args) {
        // 其日历字段已由当前日期和时间初始化：
        Calendar calendar = Calendar.getInstance(); // 子类对象
        System.out.println(getYearMonthDay(calendar));//2018年12月25日
        
        //三年前的今天
        calendar.add(Calendar.YEAR,-3);
        System.out.println(getYearMonthDay(calendar));//2015年12月25日

        //5年后的10天前
        calendar.add(Calendar.YEAR,5);
        calendar.add(Calendar.DATE,-10);
        System.out.println(getYearMonthDay(calendar));//2020年12月15日

        //设置 2011年11月11日
        calendar.set(2011,10,11);
        System.out.println(getYearMonthDay(calendar));//2011年11月11日
    }

    //获取年、月、日
    public static String getYearMonthDay(Calendar calendar){
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int date=calendar.get(Calendar.DATE);
        return year + "年" + (month + 1) + "月" + date + "日";
    }
}
```

- 小练习：获取任意一年的二月有多少天
```java
/**
 *获取任意一年的二月有多少天
 *分析：
 * 		A:键盘录入任意的年份
 * 		B:设置日历对象的年月日
 * 			年就是输入的数据
 * 			月是2
 * 			日是1
 * 		C:把时间往前推一天，就是2月的最后一天
 * 		D:获取这一天输出即可
 */
public class CalendarTest {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int year=sc.nextInt();
        Calendar c= Calendar.getInstance();
        c.set(year,2,1); //得到的就是该年的3月1日
        c.add(Calendar.DATE,-1);//把时间往前推一天，就是2月的最后一天
        //public void add(int field,int amount):根据给定的日历字段和对应的时间，来对当前的日历进行操作。

        System.out.println(year+"年，二月有"+c.get(Calendar.DATE)+"天");
    }
}
```

## Character
Character 类在对象中包装一个基本类型 char 的值.此外，该类提供了几种方法，
以确定字符的类别（小写字母，数字，等等），并将字符从大写转换成小写，反之亦然。
 
- Character常用方法：
```java
Character(char value) //构造方法
 
public static boolean isUpperCase(char ch) //判断给定的字符是否是大写字符

public static boolean isLowerCase(char ch) //判断给定的字符是否是小写字符

public static boolean isDigit(char ch) //判断给定的字符是否是数字字符

public static char toUpperCase(char ch) //把给定的字符转换为大写字符

public static char toLowerCase(char ch) //把给定的字符转换为小写字符
```
- 使用示例：
```java
public class CharacterDemo {
    public static void main(String[] args) {
        // public static boolean isUpperCase(char ch):判断给定的字符是否是大写字符
        System.out.println("isUpperCase:" + Character.isUpperCase('A'));//true
        System.out.println("isUpperCase:" + Character.isUpperCase('a'));//false
        System.out.println("isUpperCase:" + Character.isUpperCase('0'));//false
        System.out.println("-----------------------------------------");

        // public static boolean isLowerCase(char ch):判断给定的字符是否是小写字符
        System.out.println("isLowerCase:" + Character.isLowerCase('A'));//false
        System.out.println("isLowerCase:" + Character.isLowerCase('a'));//true
        System.out.println("isLowerCase:" + Character.isLowerCase('0'));//false
        System.out.println("-----------------------------------------");

        // public static boolean isDigit(char ch):判断给定的字符是否是数字字符
        System.out.println("isDigit:" + Character.isDigit('A'));//false
        System.out.println("isDigit:" + Character.isDigit('a'));//false
        System.out.println("isDigit:" + Character.isDigit('0'));//true
        System.out.println("-----------------------------------------");

        // public static char toUpperCase(char ch):把给定的字符转换为大写字符
        System.out.println("toUpperCase:" + Character.toUpperCase('A'));//A
        System.out.println("toUpperCase:" + Character.toUpperCase('a'));//A
        System.out.println("-----------------------------------------");

        // public static char toLowerCase(char ch):把给定的字符转换为小写字符
        System.out.println("toLowerCase:" + Character.toLowerCase('A'));//a
        System.out.println("toLowerCase:" + Character.toLowerCase('a'));//a
    }
}
```

- 小练习：统计一个字符串中大写字母字符，小写字母字符，数字字符出现的次数。(不考虑其他字符)

```java
/**
 *  统计一个字符串中大写字母字符，小写字母字符，数字字符出现的次数。(不考虑其他字符)
 *
 * 分析：
 * 		A:定义三个统计变量。
 * 			int bigCont=0;
 * 			int smalCount=0;
 * 			int numberCount=0;
 * 		B:键盘录入一个字符串。
 * 		C:把字符串转换为字符数组。
 * 		D:遍历字符数组获取到每一个字符
 * 		E:判断该字符是
 * 			大写	bigCount++;
 * 			小写	smalCount++;
 * 			数字	numberCount++;
 * 		F:输出结果即可
 */
public class CharacterTest {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        printCount(str);
        printCount2(str);
    }

    //原来的写法
    public static void printCount(String str) {
        int numberCount=0;
        int lowercaseCount=0;
        int upercaseCount=0;

        for(int index=0;index<str.length();index++){
            char ch=str.charAt(index);
            if(ch>='0' && ch<='9'){
                numberCount++;
            }else if(ch>='A' && ch<='Z'){
                upercaseCount++;
            }else if(ch>='a' && ch<='z'){
                lowercaseCount++;
            }
        }
        System.out.println("数字有"+numberCount+"个");
        System.out.println("小写字母有"+lowercaseCount+"个");
        System.out.println("大写字母有"+upercaseCount+"个");
    }

    //使用包装类来改进
    public static void printCount2(String str) {
        int numberCount=0;
        int lowercaseCount=0;
        int upercaseCount=0;

        for(int index=0;index<str.length();index++){
            char ch=str.charAt(index);
            if(Character.isDigit(ch)){
                numberCount++;
            }else if(Character.isUpperCase(ch)){
                upercaseCount++;
            }else if(Character.isLowerCase(ch)){
                lowercaseCount++;
            }
        }
        System.out.println("数字有"+numberCount+"个");
        System.out.println("小写字母有"+lowercaseCount+"个");
        System.out.println("大写字母有"+upercaseCount+"个");
    }
}
```

## Data_DateFormat
Date:表示特定的瞬间，精确到毫秒。

- Date常用成员方法：
```java
Date() //根据当前的默认毫秒值创建日期对象

Date(long date) //根据给定的毫秒值创建日期对象

public long getTime() //获取时间，以毫秒为单位

public void setTime(long time) //设置时间
```

- Date使用示例：
```java
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
```


DateForamt:可以进行日期和字符串的格式化和解析，但是由于是抽象类，所以使用具体子类SimpleDateFormat。
SimpleDateFormat的构造方法：

```java
SimpleDateFormat() //默认模式

SimpleDateFormat(String pattern) //给定的模式
```

这个模式字符串该如何写呢? 通过查看API，我们就找到了对应的模式:
 
| 中文说明 | 模式字符 |
| :--: | :--: |
| 年 | y |
| 月 | M |
| 日 | d |
| 时 | H |
| 分 | m |
| 秒 | s |

- Date类型和String类型的相互转换

```java
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
```
- 小练习： 算一下你来到这个世界多少天?

```java
/**
 * *
 * 算一下你来到这个世界多少天?
 *
 * 分析：
 * 		A:键盘录入你的出生的年月日
 * 		B:把该字符串转换为一个日期
 * 		C:通过该日期得到一个毫秒值
 * 		D:获取当前时间的毫秒值
 * 		E:用D-C得到一个毫秒值
 * 		F:把E的毫秒值转换为年
 * 			/1000/60/60/24
 */
public class DateTest {
    public static void main(String[] args) throws ParseException {
        // 键盘录入你的出生的年月日
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入你的出生年月日(格式 yyyy-MM-dd):");
        String line = sc.nextLine();

        // 把该字符串转换为一个日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(line);
        long birth=d.getTime(); //出生的时间
        long current=System.currentTimeMillis();//当前时间

        long days=(current-birth)/1000/60/60/24;
        System.out.println("你出生了"+days+"天");
    }
}
```

## Integer

## Object

## Scanner

## String

## StringBuffer