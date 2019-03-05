package code_09_usefulObjects.arrays;

import java.util.Arrays;

/**
 * Arrays:针对数组进行操作的工具类。比如说排序和查找。
 *      1:public static String toString(int[] a) 把数组转成字符串
 *      2:public static void sort(int[] a) 对数组进行排序
 *      3:public static int binarySearch(int[] a,int key) 二分查找
 */
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