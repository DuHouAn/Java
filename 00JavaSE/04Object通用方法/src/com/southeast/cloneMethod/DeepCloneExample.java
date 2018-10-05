package com.southeast.cloneMethod;

/**
 * （3）深拷贝
 * 拷贝对象和原始对象的引用类型引用不同对象。
 * @param <clone>
 */
public class DeepCloneExample<clone> implements Cloneable{
    private int[] arr;

    public DeepCloneExample() {
        this.arr = new int[10];
        for(int i=0;i<arr.length;i++){
            arr[i]=i;
        }
    }

    public void set(int index, int value) {
        arr[index] = value;
    }

    public int get(int index) {
        return arr[index];
    }

    @Override
    protected DeepCloneExample clone() throws CloneNotSupportedException {
        DeepCloneExample result=(DeepCloneExample)super.clone(); //result是一个新对象
        result.arr=new int[arr.length];
        for(int i=0;i<arr.length;i++){
            result.arr[i]=arr[i];
        }
        return result;
    }

    public static void main(String[] args) {
        DeepCloneExample e1=new DeepCloneExample();
        DeepCloneExample e2=null;
        try {
            e2=e1.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        e1.set(2,222);
        System.out.println(e2.get(2)); // 2 说明 e1发生变化与e2无关
    }
}
