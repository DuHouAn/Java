package com.southeast.Arrays.test4;

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
        System.out.println("排序前：" + Arrays.toString(arr));
        /**
         *  public static String toString(int[] a) {
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
         */

        // public static void sort(int[] a) 对数组进行排序
        Arrays.sort(arr);
        System.out.println("排序后：" + Arrays.toString(arr));

        // [13, 24, 57, 69, 80]
        // public static int binarySearch(int[] a,int key) 二分查找
        System.out.println("binarySearch:" + Arrays.binarySearch(arr, 57));
        System.out.println("binarySearch:" + Arrays.binarySearch(arr, 577));

        /**
         *
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
         */
    }
}
