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

- BigDecimal的常用成员方法：
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
Java就针对每一种基本数据类型提供了对应的类类型:

| 基础类型 | 包装类类型 |
| :--: | :--: |
| byte | Byte |
| short | Short |
| int |	Integer |
| long | Long |
| float | Float |
| double | Double |
| char | Character |
| boolean |	Boolean |

用于基本数据类型与字符串之间的转换。

- Integer的常用成员方法：
```java
public Integer(int value)

public Integer(String s) //注意：这个字符串必须是由数字字符组成
```

- int和String的相互转化：
```java
String.valueOf(number) //int --> String

Integer.parseInt(s)  //String --> int
```

```java
public static void main(String[] args) {
    System.out.println(intToString(100)); //100
    System.out.println(stringToInt("100")); //100
}

//int --> String
public static String intToString(int number) {
    //方式一
    /*String strNumber=""+number;
    return strNumber;*/
    //方式二
    String strNumber=String.valueOf(number);
    return strNumber;
}

//String --> int
public static int stringToInt(String strNumber) {
    //方式一
    /*Integer number=new Integer(strNumber);
    return number;*/
    //方式二
    Integer number=Integer.parseInt(strNumber);
    return number;
}
```

- 常用的进制转换：
```java
//常用的基本进制转换
public static String toBinaryString(int i)

public static String toOctalString(int i)

public static String toHexString(int i)
 
//十进制到其他进制
public static String toString(int i,int radix)//进制的范围：2-36,为什么呢?0,...9,a...z(10个数字+26个字母)

//其他进制到十进制
public static int parseInt(String s,int radix)
```

```java
public class IntegerDemo3 {
    public static void main(String[] args) {
        //test();
        //test2();
        test3();
    }

    //常用的基本进制转换
    public static void test(){
        System.out.println(Integer.toBinaryString(100));//1100100
        System.out.println(Integer.toOctalString(100));//144
        System.out.println(Integer.toHexString(100));//64
        System.out.println("-----------------------------");
    }

    //十进制到其他进制
    public static void test2(){
        System.out.println(Integer.toString(100, 10));//100
        System.out.println(Integer.toString(100, 2));//1100100
        System.out.println(Integer.toString(100, 8));//144
        System.out.println(Integer.toString(100, 16));//64
        System.out.println(Integer.toString(100, 5));//400
        System.out.println(Integer.toString(100, 7));//202
        //进制的范围在2-36之间，超过这个范围，就作为十进制处理
        System.out.println(Integer.toString(100, -7)); //100
        System.out.println(Integer.toString(100, 70));//100
        System.out.println(Integer.toString(100, 1));//100
        System.out.println(Integer.toString(100, 37));//100

        System.out.println(Integer.toString(100, 17));//5f
        System.out.println(Integer.toString(100, 32));//34
        System.out.println(Integer.toString(100, 36));//2s
        System.out.println("-------------------------");
    }

    //任意进制转换为十进制
    public static void test3(){
        System.out.println(Integer.parseInt("100", 10));//100
        System.out.println(Integer.parseInt("100", 2));//4
        System.out.println(Integer.parseInt("100", 8));//64
        System.out.println(Integer.parseInt("100", 16));//256
        System.out.println(Integer.parseInt("100", 23));//529
        //NumberFormatException,因为二进制是不可能存在 123的
        //System.out.println(Integer.parseInt("123", 2));
    }
}
```

- JDK5的新特性

自动装箱：把基本类型转换为包装类类型

自动拆箱：把包装类类型转换为基本类型

```java
public class IntegerDemo4 {
    public static void main(String[] args) {
        Integer num=null;
        if(num!=null){
            num+=100;
            System.out.println(num);
        }
        test(); //num:200
        test2(); //num:200
    }

    //自动装箱,拆箱
    public static void test() {
        Integer num=100;
        //自动装箱，实际上就是 Integer num=Integer.valueOf(100)
        num+=100;
        //先拆箱再装箱  num.intValue()+100=200 --> Integer num=Integer.valueOf(num.intValue()+100)
        System.out.println(new StringBuilder("num:").append(num).toString());
    }
    
    //手动拆装箱
    public static void test2() {
        Integer num=Integer.valueOf(100); //手动装箱
        num=Integer.valueOf(num.intValue()+100); //先手动拆箱，进行运算后，再手动装箱
        System.out.println(new StringBuilder("num:").append(num).toString());
    }
}
```

- 小练习：看程序写结果
```java
public class IntegerTest {
    public static void main(String[] args) {
        Integer i1 = new Integer(127);
        Integer i2 = new Integer(127);
        System.out.println(i1 == i2);
        System.out.println(i1.equals(i2));
        System.out.println("-----------");

        Integer i3 = new Integer(128);
        Integer i4 = new Integer(128);
        System.out.println(i3 == i4);
        System.out.println(i3.equals(i4));
        System.out.println("-----------");

        Integer i5 = 128;
        Integer i6 = 128;
        System.out.println(i5 == i6);
        System.out.println(i5.equals(i6));
        System.out.println("-----------");

        Integer i7 = 127;
        Integer i8 = 127;
        System.out.println(i7 == i8);
        System.out.println(i7.equals(i8));
    }
}
```
输出结果：
```html
false
true
-----------
false
true
-----------
false
true
-----------
true
true
```
分析：
Integer的数据直接赋值，如果在-128到127之间，会直接从缓冲池里获取数据。
通过查看源码，针对-128到127之间的数据，做了一个**数据缓冲池IntegerCache**，
如果数据是该范围内的，每次并不创建新的空间。

```java
public static Integer valueOf(int i) {
            if (i >= IntegerCache.low && i <= IntegerCache.high)
                    return IntegerCache.cache[i + (-IntegerCache.low)];
            return new Integer(i);
     }

     private static class IntegerCache {
        static final int low = -128; //low=-128
        static final int high;
        static final Integer cache[];

     static {
             // high value may be configured by property
            int h = 127;
           //...
     }
     high = h; //high=127
```
## Object
Object类是类层次结构的根类。每个类都使用 Object 作为超类。每个类都直接或者间接的继承自Object类。

- Object类的方法:
```java
public int hashCode() //返回该对象的哈希码值。
// 注意：哈希值是根据哈希算法计算出来的一个值，这个值和地址值有关，但是不是实际地址值。

public final Class getClass() //返回此 Object 的运行时类

public String toString() //返回该对象的字符串表示。

protected Object clone() //创建并返回此对象的一个副本。可重写该方法

protected void finalize() 
//当垃圾回收器确定不存在对该对象的更多引用时，由对象的垃圾回收器调用此方法。用于垃圾回收，但是什么时候回收不确定。
```

- [Object的使用 代码示例](https://github.com/DuHouAn/Java/tree/master/JavaBasics/src/code_03_Object)

## Scanner
Scanner:用于接收键盘录入数据。

- 使用Scanner三部曲：

A:导包

B :创建对象

C :使用相应方法

- Scanner常用成员方法
```java
Scanner(InputStream source) //构造方法

public boolean hasNextXxx() //判断是否是某种类型的元素,Xxx表示类型，比如 public boolean hasNextInt()

public Xxx nextXxx() //获取该元素，Xxx表示类型，比如public int nextInt()
```
注意：InputMismatchException：表示输入的和你想要的不匹配

- 使用示例1：
```java
public class ScannerDemo {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        if(sc.hasNextInt()){
            int x=sc.nextInt();
            System.out.println("x="+x);
        }else{
            System.out.println("输入的数据有误");
        }
    }
}
```

- 先获取一个数值，换行后，再获取一个字符串，会出现问题。主要原因：就是换行符号的问题。如何解决呢？
```java
public static void main(String[] args) {
    Scanner sc=new Scanner(System.in);

    //输入 12 换行后在输出 "sss"
    int x=sc.nextInt();
    System.out.println("x:"+x); //x:12

    String line=sc.nextLine(); //这里会有问题 因为会将换行符当作字符输输入
    System.out.println("line:"+line); //line:
}
```

> 解决方案一:先获取一个数值后，再创建一个新的键盘录入对象获取字符串。

```java
public static void method() {
    Scanner sc=new Scanner(System.in);
    int x=sc.nextInt();
    System.out.println("x:"+x);

    Scanner sc2=new Scanner(System.in);
    String line=sc2.nextLine();
    System.out.println("line:"+line);
}
```

> 解决方案二：把所有的数据都先按照字符串获取，然后要什么，就进行相应的转换。

```java
public static void method2() {
    Scanner sc=new Scanner(System.in);

    String xStr=sc.nextLine();
    String line=sc.nextLine();

    int x=Integer.parseInt(xStr);
    
    System.out.println("x:"+x);
    System.out.println("line:"+line);
}
```
## String
字符串：就是由多个字符组成的一串数据。也可以看成是一个字符数组。

- **String的构造方法方法**：
```java
//String的构造方法
public String() //空构造

public String(byte[] bytes) //把字节数组转成字符串

public String(byte[] bytes,int index,int length) //把字节数组的一部分转成字符串

public String(char[] value) //把字符数组转成字符串

public String(char[] value,int index,int count) //把字符数组的一部分转成字符串,第三个参数表示的是数目

public String(String original) //把字符串常量值转成字符串
```

```java
public class StringDemo {
    public static void main(String[] args) {
        //public String():空构造
        String s=new String();
        System.out.println("s:"+s);
        System.out.println("s.length="+s.length());
        System.out.println("-----------------------");

        //public String(byte[] bytes):把字节数组转成字符串
        byte[] bys={97,98,99,100,101};

        String s2=new String(bys);
        System.out.println("s2:"+s2);
        System.out.println("s2.length="+s2.length());
        System.out.println("-----------------------");

        //public String(byte[] bytes,int index,int length):把字节数组的一部分转成字符串
        String s3=new String(bys,0,3); //从0位置开始,3个字符
        System.out.println("s3:"+s3);//s3:abc
        System.out.println("s3.length="+s3.length());//s3.length=3
        System.out.println("-----------------------");


        //public String(char[] value):把字符数组转成字符串
        char[] chs={'a','b','c','d','e'};
        String s4=new String(chs); //从0位置开始,3个字符
        System.out.println("s4:"+s4);
        System.out.println("s4.length="+s4.length());
        System.out.println("-----------------------");

        //public String(char[] value,int index,int count):把字符数组的一部分转成字符串
        String s5=new String(chs,0,3); //从0位置开始,3个字符
        System.out.println("s5:"+s5);
        System.out.println("s5.length="+s5.length());
        System.out.println("-----------------------");

        //public String(String original):把字符串常量值转成字符串
        String s6=new String("abcde");
        System.out.println("s6:"+s6);
        System.out.println("s6.length="+s6.length());
    }
}
```

- 字符串的特点：一旦被赋值，就不能改变
```java
public static void test() {
    String s = "hello";
    s += "world";
    System.out.println("s:" + s); // helloworld
}
```

- 小练习：看程序，写结果：
```java
public static void test2(){
    String s1 = new String("hello");
    String s2 = new String("hello");
    System.out.println(s1 == s2);
    System.out.println(s1.equals(s2));

    String s3 = new String("hello");
    String s4 = "hello";
    System.out.println(s3 == s4);
    System.out.println(s3.equals(s4));

    String s5 = "hello";
    String s6 = "hello";
    System.out.println(s5 == s6);
    System.out.println(s5.equals(s6));
}
```

输出结果：
```html
false
true
false
true
true
true
```
分析：
> =和qeuals的区别

==:比较引用类型，比较的是地址值是否相同

equals:比较引用类型，默认也是比较地址值是否相同

String类重写了equals()方法，比较的是内容是否相同。

> String s = new String(“hello”)和String s = “hello”的区别？
 
前者会创建2个对象，后者创建1个对象。更具体的说，前者会创建2个或者1个对象，后者会创建1个或者0个对象。

- **String类的判断功能**：
```java
boolean equals(Object obj) //比较字符串的内容是否相同,区分大小写

boolean equalsIgnoreCase(String str) //比较字符串的内容是否相同,忽略大小写

boolean contains(String str) //判断大字符串中是否包含小字符串

boolean startsWith(String str) //判断字符串是否以某个指定的字符串开头

boolean endsWith(String str) //判断字符串是否以某个指定的字符串结尾

boolean isEmpty()// 判断字符串是否为空
```
注意：字符串内容为空和字符串对象为空。
```java
String s = "";//字符串内容为空
String s = null;//字符串对象为空
```

```java
public class StringDemo3 {
    public static void main(String[] args) {
        // 创建字符串对象
        String s1 = "helloworld";
        String s2 = "helloworld";
        String s3 = "HelloWorld";

        // boolean equals(Object obj):比较字符串的内容是否相同,区分大小写
        System.out.println("equals:" + s1.equals(s2));//true
        System.out.println("equals:" + s1.equals(s3));//false
        System.out.println("-----------------------");

        // boolean equalsIgnoreCase(String str):比较字符串的内容是否相同,忽略大小写
        System.out.println("equals:" + s1.equalsIgnoreCase(s2));//true
        System.out.println("equals:" + s1.equalsIgnoreCase(s3));//true
        System.out.println("-----------------------");

        // boolean contains(String str):判断大字符串中是否包含小字符串
        System.out.println("contains:" + s1.contains("hello"));//true
        System.out.println("contains:" + s1.contains("hw"));//false
        System.out.println("-----------------------");

        // boolean startsWith(String str):判断字符串是否以某个指定的字符串开头
        System.out.println("startsWith:" + s1.startsWith("h"));//true
        System.out.println("startsWith:" + s1.startsWith("hello"));//true
        System.out.println("startsWith:" + s1.startsWith("world"));//false
        System.out.println("-----------------------");

        //boolean endsWith(String str):判断字符串是否以某个指定的字符串结尾
        System.out.println("startsWith:" + s1.endsWith("d"));//true
        System.out.println("startsWith:" + s1.endsWith("world"));//true
        System.out.println("startsWith:" + s1.endsWith("hello"));//false
        System.out.println("-----------------------");

        // boolean isEmpty():判断字符串是否为空。
        System.out.println("isEmpty:" + s1.isEmpty());//false

        String s4 = ""; //字符串内容为空
        String s5 = null;//字符串对象为空
        System.out.println("isEmpty:" + s4.isEmpty());//true
        // NullPointerException
        // s5对象都不存在，所以不能调用方法，空指针异常
        //System.out.println("isEmpty:" + s5.isEmpty());
    }
}
```

- **String类的获取功能**:
```java
int length() //获取字符串的长度。

char charAt(int index) //获取指定索引位置的字符

int indexOf(int ch) //返回指定字符在此字符串中第一次出现处的索引。为什么这里参数int类型，而不是char类型?原因是：'a'和97其实都可以代表'a'

int indexOf(String str) //返回指定字符串在此字符串中第一次出现处的索引。

int indexOf(int ch,int fromIndex) //返回指定字符在此字符串中从指定位置后第一次出现处的索引。

int indexOf(String str,int fromIndex) //返回指定字符串在此字符串中从指定位置后第一次出现处的索引。

String substring(int start) //从指定位置开始截取字符串,默认到末尾。

String substring(int start,int end) //从指定位置开始到指定位置结束截取字符串。左闭右开
```

```java
public class StringDemo4 {
    public static void main(String[] args) {
        // 定义一个字符串对象
        String s = "helloworld";

        // int length():获取字符串的长度。
        System.out.println("s.length:" + s.length());//10
        System.out.println("----------------------");

        // char charAt(int index):获取指定索引位置的字符
        System.out.println("charAt:" + s.charAt(7));//r
        System.out.println("----------------------");

        // int indexOf(int ch):返回指定字符在此字符串中第一次出现处的索引。
        System.out.println("indexOf:" + s.indexOf('l'));//2
        System.out.println("----------------------");

        // int indexOf(String str):返回指定字符串在此字符串中第一次出现处的索引。
        System.out.println("indexOf:" + s.indexOf("owo"));//4
        System.out.println("----------------------");

        // int indexOf(int ch,int fromIndex):返回指定字符在此字符串中从指定位置后第一次出现处的索引。
        System.out.println("indexOf:" + s.indexOf('l', 4));//8
        System.out.println("indexOf:" + s.indexOf('k', 4)); // -1
        System.out.println("indexOf:" + s.indexOf('l', 40)); // -1
        System.out.println("----------------------");

        // int indexOf(String str,intfromIndex):返回指定字符串在此字符串中从指定位置后第一次出现处的索引。
        System.out.println("indexOf:" + s.indexOf("owo", 4));//4
        System.out.println("indexOf:" + s.indexOf("ll", 4)); //-1
        System.out.println("indexOf:" + s.indexOf("ld", 40)); // -1
        System.out.println("----------------------");

        // String substring(int start):从指定位置开始截取字符串,默认到末尾。包含start这个索引
        System.out.println("substring:" + s.substring(5));//world
        System.out.println("substring:" + s.substring(0));//helloworld
        System.out.println("----------------------");

        // String substring(int start,int
        // end):从指定位置开始到指定位置结束截取字符串。包括start索引但是不包end索引
        System.out.println("substring:" + s.substring(3, 8));//lowor
        System.out.println("substring:" + s.substring(0, s.length()));//helloworld

        /**
         * 获取 字符串中的每个字符
         */
        for(int i=0;i<s.length();i++){
            System.out.println(s.charAt(i));
        }
    }
}
```

- **String的转换功能**
```java
byte[] getBytes() //字符串转换为字节数组。 

char[] toCharArray() //把字符串转换为字符数组。

static String valueOf(char[] chs) //把字符数组转成字符串。

static String valueOf(int i) //把int类型的数据转成字符串。注意：String类的valueOf方法可以把任意类型的数据转成字符串。

String toLowerCase() //把字符串转成小写。

String toUpperCase() //把字符串转成大写。

String concat(String str) //把字符串拼接。
```

```java
public class StringDemo5 {
    public static void main(String[] args) {
        // 定义一个字符串对象
        String s = "JavaSE";

        // byte[] getBytes():把字符串转换为字节数组。
        byte[] bys = s.getBytes();
        for (int x = 0; x < bys.length; x++) {
            System.out.println(bys[x]);//74 97 118 97 83 69
        }
        System.out.println("----------------");

        // char[] toCharArray():把字符串转换为字符数组。
        char[] chs = s.toCharArray();
        for (int x = 0; x < chs.length; x++) {
            System.out.println(chs[x]);//J a v a S E
        }
        System.out.println("----------------");

        // static String valueOf(char[] chs):把字符数组转成字符串。
        String ss = String.valueOf(chs);
        System.out.println(ss);//JavaSE
        System.out.println("----------------");

        // static String valueOf(int i):把int类型的数据转成字符串。
        int i = 100;
        String sss = String.valueOf(i);
        System.out.println(sss);//100 是字符串
        System.out.println("----------------");

        // String toLowerCase():把字符串转成小写。
        System.out.println("toLowerCase:" + s.toLowerCase());//javase
        System.out.println("s:" + s);
        // System.out.println("----------------");
        // String toUpperCase():把字符串转成大写。
        System.out.println("toUpperCase:" + s.toUpperCase());//JAVASE
        System.out.println("----------------");

        // String concat(String str):把字符串拼接。
        String s1 = "hello";
        String s2 = "world";
        String s3 = s1 + s2;
        String s4 = s1.concat(s2);
        System.out.println("s3:"+s3);//helloworld
        System.out.println("s4:"+s4);//helloworld
    }
}
```

- 练习：将一个字符串的首字母转成大写，其余为小写。(只考虑英文大小写字母字符)
```java
/**
 * 需求：把一个字符串的首字母转成大写，其余为小写。(只考虑英文大小写字母字符)
 * 举例：
 * 		helloWORLD
 * 结果：
 * 		Helloworld
 *
 * 		A:先获取第一个字符
 * 		B:获取除了第一个字符以外的字符
 * 		C:把A转成大写
 * 		D:把B转成小写
 * 		E:C拼接D
 */
public class StringTest {
    public static void main(String[] args) {
        String str="helloWORLD";
        str=str.substring(0,1).toUpperCase().
                concat(str.substring(1).toLowerCase());
        System.out.println(str);
    }
}
```

- **String类的其他功能**:
```java
//替换功能：
String replace(char old,char new)

String replace(String old,String new)

//去除字符串两端空格
String trim()

//按字典顺序比较两个字符串
int compareTo(String str)
int compareToIgnoreCase(String str)
```

> compareTo()方法源码解析

```java
private final char value[]; //字符串会自动转换为一个字符数组。
/**
* 举例
  String s6 = "hello";
  String s9 = "xyz";
  System.out.println(s6.compareTo(s9));// -16
*/
public int compareTo(String anotherString) {
    int len1 = value.length;
    int len2 = anotherString.value.length;

    int lim = Math.min(len1, len2); //lim=3
    char v1[] = value; //v1[h e l l o]
    char v2[] = anotherString.value;//v2[x y z]

    int k = 0;
    while (k < lim) {
        char c1 = v1[k];//h
        char c2 = v2[k];//x
        if (c1 != c2) { //先比较的是对应的字符是否相等
            return c1 - c2; //h=104  x=120   104-120=-16
        }
        k++;
    }
    //如果在指定位置，字符都相同，则长度相减
    return len1 - len2;
}
```

- 练习1：把数组中的数据按照指定个格式拼接成一个字符串。举例：int[] arr = {1,2,3};输出结果：[1, 2, 3]
```java
/**
 *
 * 需求：把数组中的数据按照指定个格式拼接成一个字符串
 * 举例：
 * 		int[] arr = {1,2,3};
 * 输出结果：
 *		"[1, 2, 3]"
 * 分析：
 * 		A:定义一个字符串对象，只不过内容为空
 * 		B:先把字符串拼接一个"["
 * 		C:遍历int数组，得到每一个元素
 * 		D:先判断该元素是否为最后一个
 * 			是：就直接拼接元素和"]"
 * 			不是：就拼接元素和逗号以及空格
 * 		E:输出拼接后的字符串
 */
public class StringTest2 {
    public static void main(String[] args) {
        int[] arr={1,2,3,4};
        System.out.println(arrayToString(arr));
    }

    /**
     * 把数组中的数据按照指定个格式拼接成一个字符串
     */
    public static String arrayToString(int[] arr){
        String str="[";
        for(int i=0;i<arr.length;i++){
            if(i==arr.length-1){
                str+=arr[i];
            }else{
                str+=arr[i]+",";
            }
        }
        str+="]";
        return str;
    }
}
```

-  练习2：统计大串中小串出现的次数
```java
/**
 * 统计大串中小串出现的次数
 * 举例：
 * 		在字符串"woaijavawozhenaijavawozhendeaijavawozhendehenaijavaxinbuxinwoaijavagun"
 * 结果：
 * 		java出现了5次
 *
 * 分析：
 * 		前提：是已经知道了大串和小串。
 * 		A:定义一个统计变量，初始化值是0
 * 		B:先在大串中查找一次小串第一次出现的位置 ( boolean contains(String str):判断大字符串中是否包含小字符串)
 * 			a:索引是-1，说明不存在了，就返回统计变量
 * 			b:索引不是-1，说明存在，统计变量++
 * 		C:把刚才的索引+小串的长度作为开始位置截取上一次的大串，返回一个新的字符串，并把该字符串的值重新赋值给大串
 * 		D:回到B
 */
public class StringTest3 {
    public static void main(String[] args) {
        String s1="woaijavawozhenaijavawozhendeaijavawozhendehenaijavaxinbuxinwoaijavagun";
        String s2="java";
        System.out.println(smallCount(s2,s1));
    }

    // 统计大串中小串出现的次数
    public static int smallCount(String smallStr,String bigStr) {
        int count=0;

        int index=bigStr.indexOf(smallStr);
        while(index!=-1){
            count++;
            //把刚才的索引+小串的长度作为开始位置截取上一次的大串
            int startIndex=index+smallStr.length();
            bigStr=bigStr.substring(startIndex);
            index=bigStr.indexOf(smallStr);
        }
        return count;
    }
}
```

## StringBuffer

-  **StringBuffer的常用成员方法**：
```java
//StirngBuffer的构造方法
public StringBuffer() //无参构造方法

public StringBuffer(int capacity) //指定容量的字符串缓冲区对象

public StringBuffer(String str) //指定字符串内容的字符串缓冲区对象

//StringBuffer的方法
public int capacity() //返回当前容量。	理论值

public int length() //返回长度（字符数）。 实际值
```
```java
public static void main(String[] args) {
    // public StringBuffer():无参构造方法
    StringBuffer sb = new StringBuffer();
    System.out.println("sb:" + sb);
    System.out.println("sb.capacity():" + sb.capacity()); //StringBuffer默认的指定容量是16
    System.out.println("sb.length():" + sb.length());//0
    System.out.println("--------------------------");

    // public StringBuffer(int capacity):指定容量的字符串缓冲区对象
    StringBuffer sb2 = new StringBuffer(50);
    System.out.println("sb2:" + sb2);//""
    System.out.println("sb2.capacity():" + sb2.capacity());//50
    System.out.println("sb2.length():" + sb2.length());//0
    System.out.println("--------------------------");

    // public StringBuffer(String str):指定字符串内容的字符串缓冲区对象
    StringBuffer sb3 = new StringBuffer("hello");
    System.out.println("sb3:" + sb3);//hello
    System.out.println("sb3.capacity():" + sb3.capacity());//16+5=21
    System.out.println("sb3.length():" + sb3.length());//5
}
```

- **StringBuffer的添加功能**:
```java
public StringBuffer append(String str) //可以把任意类型数据添加到字符串缓冲区里面,并返回字符串缓冲区本身

public StringBuffer insert(int offset,String str) //在指定位置把任意类型的数据插入到字符串缓冲区里面,并返回字符串缓冲区本身
```

```java
public static void main(String[] args) {
    // 创建字符串缓冲区对象
    StringBuffer sb = new StringBuffer();
    
    // 链式编程
    sb.append("hello").append(true).append(12).append(34.56);
    System.out.println("sb:" + sb);

    // public StringBuffer insert(int offset,String
    // str):在指定位置把任意类型的数据插入到字符串缓冲区里面,并返回字符串缓冲区本身
    sb.insert(5, "world");
    System.out.println("sb:" + sb);
}
```

- **StringBuffer的删除功能**
```java
public StringBuffer deleteCharAt(int index) //删除指定位置的字符，并返回本身

public StringBuffer delete(int start,int end) //删除从指定位置开始指定位置结束的内容，并返回本身
```
```java
public static void main(String[] args) {
    // 创建对象
    StringBuffer sb = new StringBuffer();

    // 添加功能
    sb.append("hello").append("world").append("java");

    // public StringBuffer deleteCharAt(int index):删除指定位置的字符，并返回本身
    // 需求：我要删除e这个字符，肿么办?
    //sb.deleteCharAt(1);
    //public StringBuffer delete(int start,int end):删除从指定位置开始指定位置结束的内容，并返回本身
    // 需求：我要删除world这个字符串，肿么办?
    //sb.delete(5, 10);
    //需求:删除所有字符
    sb.delete(0,sb.length());
    System.out.println("sb:" + sb);
}
```

- **StringBuffer的替换功能**
```java
public StringBuffer replace(int start,int end,String str) //从start开始到end用str替换
```
```java
public static void main(String[] args) {
    // 创建字符串缓冲区对象
    StringBuffer sb = new StringBuffer();

    // 添加数据
    sb.append("hello");
    sb.append("world");
    sb.append("java");
    System.out.println("sb:" + sb);

    // public StringBuffer replace(int start,int end,String str):从start开始到end用str替换
    // 需求：我要把world这个数据替换为"节日快乐"
    sb.replace(5,10,"节日快乐");
    System.out.println("sb："+sb);
}
```

- **StringBuffer的反转功能**：
```java
public StringBuffer reverse()
```
```java
public static void main(String[] args) {
    // 创建字符串缓冲区对象
    StringBuffer sb = new StringBuffer();

    // 添加数据
    sb.append("霞青林爱我");
    System.out.println("sb:" + sb);//霞青林爱我

    // public StringBuffer reverse()
    sb.reverse();
    System.out.println("sb:" + sb);//我爱林青霞
}
```

- **StringBuffer的截取功能**:

```java
public String substring(int start) //TODO：注意截取返回的是String,而不是StringBuffer了

public String substring(int start,int end)
```
```java
public static void main(String[] args) {
    // 创建字符串缓冲区对象
    StringBuffer sb = new StringBuffer();

    // 添加元素
    sb.append("hello").append("world").append("java");
    System.out.println("sb:" + sb);//sb:helloworldjava

    // 截取功能
    // public String substring(int start)
    String s = sb.substring(5);
    System.out.println("s:" + s);//s:worldjava
    System.out.println("sb:" + sb);//sb:helloworldjava

    // public String substring(int start,int end)
    String ss = sb.substring(5, 10);//左闭右开
    System.out.println("ss:" + ss);//ss:world
    System.out.println("sb:" + sb);//sb:helloworldjava
}
```

- **String和StringBuffer的相互转换**:
```java
public class StringBufferDemo7 {
    public static void main(String[] args) {
        String s="hello";
        System.out.println(stringToStringBuffer(s)); //hello
        System.out.println(stringBufferToString(stringToStringBuffer(s)));//hello
    }

    // String -->StringBuffer
    public static StringBuffer stringToStringBuffer(String s) {
        // 注意：不能把字符串的值直接赋值给StringBuffer
        // StringBuffer sb = "hello";
        // StringBuffer sb = s;

        // 方式1:通过构造方法
      /*  StringBuffer sb = new StringBuffer(s);
        return sb;*/
        // 方式2：通过append()方法
        StringBuffer sb2 = new StringBuffer();
        sb2.append(s);
        return sb2;
    }

    //StringBuffer -->String
    public static String stringBufferToString(StringBuffer buffer){
        // 方式1:通过构造方法
       /* String str = new String(buffer);
        return str;*/
        // 方式2：通过toString()方法
        String str2 = buffer.toString();
        return str2;
    }
}
```

- 练习1：将数组拼接成一个字符串

```java
public class StringBufferTest {
    public static void main(String[] args) {
        int[] arr={1,2,3,4};
        System.out.println(arrToString(arr));//[1,2,3,4]
    }

    public static String arrToString(int[] arr){
        StringBuffer buffer=new StringBuffer();
        buffer.append("[");
        for(int i=0;i<arr.length;i++){
            if(i==arr.length-1){
                buffer.append(arr[i]);
            }else{
                buffer.append(arr[i]).append(",");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
}
```

- 练习2：判断一个字符串是否是对称字符串
```java
/**
 *  判断一个字符串是否是对称字符串
 * 例如"abc"不是对称字符串，"aba"、"abba"、"aaa"、"mnanm"是对称字符串
 *
 * 分析：
 * 		判断一个字符串是否是对称的字符串，我只需要把
 * 			第一个和最后一个比较
 * 			第二个和倒数第二个比较
 * 			...
 * 		比较的次数是长度除以2。
 */
public class StringBufferTest2 {
    public static void main(String[] args) {
        System.out.println(isSymmetry("aba")); //true
        System.out.println(isSymmetry2("aabbaa"));//true
    }

    //通过字符数组的方式来比较
    public static boolean isSymmetry(String s){
        boolean flag=true;
        char[] chs=s.toCharArray();
        int len=chs.length;
        for(int start=0,end=chs.length-1;start<=end;start++,end--){
            if(chs[start]!=chs[end]){
                flag=false;
                break;
            }
        }
        return flag;
    }

     //通过StringBuffer的reverse
    public static boolean isSymmetry2(String s){
        //通过StringBuffer的reverse获取反转字符串s2
        String s2=new StringBuffer(s).reverse().toString();
        return s2.equals(s);
    }
}
```
- 练习3：看程序写结果
```java
public class StringBufferTest3 {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "world";
        System.out.println(s1 + "---" + s2);
        change(s1, s2);
        System.out.println(s1 + "---" + s2);

        StringBuffer sb1 = new StringBuffer("hello");
        StringBuffer sb2 = new StringBuffer("world");
        System.out.println(sb1 + "---" + sb2);
        change(sb1, sb2);
        System.out.println(sb1 + "---" + sb2);
    }

    //StringBuffer作为参数传递
    public static void change(StringBuffer sb1, StringBuffer sb2) {
        sb1 = sb2;
        sb2.append(sb1);
    }

    //String作为参数传递
    public static void change(String s1, String s2) {
        s1 = s2;
        s2 = s1 + s2;
    }
}
```
输出结果：
```html
hello---world
hello---world
hello---world
hello---worldworld
```
String作为参数传递，效果和基本类型作为参数传递是一样的。