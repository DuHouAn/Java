package code_09_usefulObjects.scanner;

import java.util.Scanner;

/**
 *常用的两个方法：
 * 		public int nextInt():获取一个int类型的值
 * 		public String nextLine():获取一个String类型的值
 *
 * 出现问题了：
 * 		先获取一个数值，在获取一个字符串，会出现问题。
 * 		主要原因：就是那个换行符号的问题。
 *
 * 如何解决呢?
 * 		A:先获取一个数值后，在创建一个新的键盘录入对象获取字符串。
 * 		B:把所有的数据都先按照字符串获取，然后要什么，你就对应的转换为什么。
 */
public class ScannerDemo2 {
    public static void main(String[] args) {
        /*Scanner sc=new Scanner(System.in);

        int x=sc.nextInt();
        System.out.println("x:"+x);

        String line=sc.nextLine(); //这里会有问题 因为会将换行符当作字符输输入
        System.out.println("line:"+line);*/
        method();
        method2();
    }

    //解决方案一:先获取一个数值后，再创建一个新的键盘录入对象获取字符串。
    public static void method() {
        Scanner sc=new Scanner(System.in);
        int x=sc.nextInt();
        System.out.println("x:"+x);

        Scanner sc2=new Scanner(System.in);
        String line=sc2.nextLine();
        System.out.println("line:"+line);
    }

    //解决方案二：把所有的数据都先按照字符串获取，然后要什么，就进行相应的转换
    public static void method2() {
        Scanner sc=new Scanner(System.in);

        String xStr=sc.nextLine();
        String line=sc.nextLine();

        int x=Integer.parseInt(xStr);

        System.out.println("x:"+x);
        System.out.println("line:"+line);
    }
}
