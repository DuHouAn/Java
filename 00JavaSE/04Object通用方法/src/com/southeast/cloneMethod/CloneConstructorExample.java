package com.southeast.cloneMethod;

/**
 * （4）Clone()替代方案 类比 DeepCloneExample.java
 * 使用clone()方法来拷贝一个对象即复杂又有风险，它会抛出异常，并且还需要类型转换.
 * Effective Java 书上讲到，最好不要去使用 clone()，可以使用
 * ====拷贝构造函数===或者===拷贝工厂==来拷贝一个对象。
 */
public class CloneConstructorExample {
    private int[] arr;

    public CloneConstructorExample() { //初始化构造方法
        arr=new int[10];
        for(int i=0;i<arr.length;i++){
            arr[i]=i;
        }
    }

    public CloneConstructorExample(CloneConstructorExample original) { //拷贝构造函数
        arr=new int[original.arr.length];
        for(int i=0;i<original.arr.length;i++){
            arr[i]=original.arr[i];
        }
    }

    public void set(int index, int value) {
        arr[index] = value;
    }

    public int get(int index) {
        return arr[index];
    }

    public static void main(String[] args) {
        CloneConstructorExample e1=new CloneConstructorExample();
        CloneConstructorExample e2=new CloneConstructorExample(e1);
        e1.set(2,222);
        System.out.println(e2.get(2));  //2
    }
}
