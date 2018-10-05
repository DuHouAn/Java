package com.southeast.Arrays.test1;

/**
 * Created by 18351 on 2018/8/31.
 */
public class ArrayDemo {
    public static void main(String[] args) {
        int[] arr={3,1,2,6,0};
        printArray(arr);

        //bubbleSort(arr);
        selectSort(arr);

        printArray(arr);
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

    //选择排序
    public static void selectSort(int[] arr) {
        for(int i=0;i<arr.length-1;i++){ //从第一个元素开始遍历数组
           for(int j=i+1;j<arr.length;j++){
               if(arr[i]>arr[j]){
                   swap(arr,i,j);
               }
           }
        }
    }

    public static void swap(int[] arr,int i,int j){
        arr[i]=arr[i]^arr[j];
        arr[j]=arr[i]^arr[j];
        arr[i]=arr[i]^arr[j];
    }

    //遍历数组
    public static void printArray(int[] arr){
        System.out.print("[");
        for(int i=0;i<arr.length;i++){
            if(i==arr.length-1){
                System.out.print(arr[i]);
            }else{
                System.out.print(arr[i]+",");
            }
        }
        System.out.println("]");
    }
}
