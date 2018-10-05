package com.southeast.cloneMethod;

/**
 * （2）浅拷贝
 * 拷贝对象和原始对象的引用类型引用同一个对象。
 */
public class ShallowCloneExample implements Cloneable{
    private int[] arr;

    public ShallowCloneExample() {
        arr=new int[10];
        for(int i=0;i<arr.length;i++){
            arr[i]=i;
        }
    }

    public void set(int index,int value){
        arr[index]=value;
    }

    public int get(int index){
        return arr[index];
    }

    @Override
    protected ShallowCloneExample clone() throws CloneNotSupportedException {
        return (ShallowCloneExample) super.clone(); //返回的是同一个对象
    }

    public static void main(String[] args) {
        ShallowCloneExample example=new ShallowCloneExample();
        ShallowCloneExample example2=null;
        try {
            example2=example.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        example.set(2,222); //2下标位置发生变化，数组中元素就发生变化
        System.out.println(example2.get(2));
    }
}
