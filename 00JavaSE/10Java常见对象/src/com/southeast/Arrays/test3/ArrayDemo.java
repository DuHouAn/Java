package com.southeast.Arrays.test3;

/**
 *  查找：
 * 		基本查找:数组元素无序(从头找到尾)
 * 		二分查找(折半查找):数组元素有序
 *
 * 分析：
 * 		A:定义最大索引，最小索引
 * 		B:计算出中间索引
 * 		C:拿中间索引的值和要查找的值进行比较
 * 			相等：就返回当前的中间索引
 * 			不相等：
 * 				大	左边找
 * 				小	右边找
 * 		D:重新计算出中间索引
 * 			大	左边找
 * 				max = mid - 1;
 * 			小	右边找
 * 				min = mid + 1;
 * 		E:回到B
 */
public class ArrayDemo {
    public static void main(String[] args) {
        //定义一个数组
        int[] arr = { 24, 69, 80, 57, 13 };

        // 先排序
        bubbleSort(arr);

        //写功能实现
        int index = getIndex(arr, 80);
        System.out.println("index:"+index);

        //假如这个元素不存在后有什么现象呢?
        index = getIndex(arr, 333);
        System.out.println("index:"+index);
    }

    //冒泡排序
    public static void bubbleSort(int[] arr) {
        for(int i=0;i<arr.length-1;i++){
            for(int j=0;j<arr.length-i-1;j++){
                if(arr[j]>arr[j+1]){
                    swap(arr,j,j+1);
                }
            }
        }
    }

    public static void swap(int[] arr,int i,int j){
        arr[i]=arr[i]^arr[j];
        arr[j]=arr[i]^arr[j];
        arr[i]=arr[i]^arr[j];
    }
    //二分查找，元素已有序
    //找到该元素，返回该元素下标；没找到该元素，返回-1
    public static int getIndex(int[] arr,int key) {
        int low=0;
        int high=arr.length-1;

        while(low<=high){
            int mid=(low+high)>>1;
            if(key>arr[mid]){
                low=mid+1;
            }else if(key<arr[mid]){
                high=mid-1;
            }else{
                return mid;
            }
        }
        return -1; //找不到该元素，元素就返回-1
    }
}
