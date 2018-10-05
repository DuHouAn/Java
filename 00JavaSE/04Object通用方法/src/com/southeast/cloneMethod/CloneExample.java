package com.southeast.cloneMethod;

/**
 * clone()是Object的protected方法，它不是 public,
 * 一个类显示区重写clone(),其他类就不能直接去调用该类实例的clone()方法。
 */
public class CloneExample implements Cloneable{ //这里实现了Cloneable接口
    private int a;
    private int b;

    @Override
    protected CloneExample clone() throws CloneNotSupportedException {
        return (CloneExample) super.clone();
    }

    /**
     * Cloneable 接口只是规定，
     * 如果一个类没有实现 Cloneable 接口又调用了 clone() 方法，
     * 就会抛出 CloneNotSupportedException。
     */
    public static void main(String[] args) {
        CloneExample e1=new CloneExample();
        try {
            CloneExample e2= e1.clone();
            //抛出 java.lang.CloneNotSupportedException: com.southeast.cloneMethod.CloneExample
            //以上抛出了 CloneNotSupportedException，这是因为 CloneExample 没有实现 Cloneable 接口。
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
