package com.southeast.Arrays.test2;

/**
 *  把字符串中的字符进行排序。
 * 		举例："dacgebf"
 * 		结果："abcdefg"
 */
public class ArrayTest {
    public static void main(String[] args) {
        String s="dacgebf";
        char[] chs=s.toCharArray();
        selectSort(chs);

        //把排序后的字符数组转成字符串
        String result = String.valueOf(chs);
        //输出最后的字符串
        System.out.println("result:"+result);
    }

    public static void selectSort(char[] arr) {
        for(int i=0;i<arr.length-1;i++){ //从第一个元素开始遍历数组
            for(int j=i+1;j<arr.length;j++){
                if(arr[i]>arr[j]){
                    swap(arr,i,j);
                }
            }
        }
    }

    public static void swap(char[] arr,int i,int j){
        char tmp=arr[i];
        arr[i]=arr[j];
        arr[j]=tmp;
    }
}
