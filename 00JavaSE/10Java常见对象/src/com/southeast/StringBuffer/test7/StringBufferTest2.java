package com.southeast.StringBuffer.test7;

/**
 * 把数组拼接成一个字符串
 */
public class StringBufferTest2 {
    public static void main(String[] args) {
        int[] arr={1,2,3,4,5};
        System.out.println(arrayToStringByString(arr));
        System.out.println(arrayToStringByStringbUffer(arr));
    }

    //通过String方式进行字符串的拼接
    public static String  arrayToStringByString(int[] arr){
        String s="";
        s+="[";
        for(int index=0;index<arr.length;index++){
            if(index==arr.length-1){
                s+=arr[index];
            }else{
                s+=arr[index]+",";
            }
        }
        s+="]";
        return s;
    }

    public static String arrayToStringByStringbUffer(int[] arr) {
        StringBuffer sb=new StringBuffer();
        sb.append("[");
        for(int index=0;index<arr.length;index++){
            if(index==arr.length-1){
                sb.append(arr[index]);
            }else{
                sb.append(arr[index]).append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
