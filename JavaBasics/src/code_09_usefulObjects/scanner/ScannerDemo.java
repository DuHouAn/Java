package code_09_usefulObjects.scanner;

import java.util.Scanner;

/**
 *  基本格式：
 * 		public boolean hasNextXxx():判断是否是某种类型的元素
 * 		public Xxx nextXxx():获取该元素
 *
 * 举例：用int类型的方法举例
 * 		public boolean hasNextInt()
 * 		public int nextInt()
 *
 * 注意：
 * 		InputMismatchException：输入的和你想要的不匹配
 */
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
