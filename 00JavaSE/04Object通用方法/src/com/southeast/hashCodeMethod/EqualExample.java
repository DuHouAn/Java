package com.southeast.hashCodeMethod;

/**
 * 在覆盖 equals() 方法时应当总是覆盖 hashCode() 方法，
 * 保证==等价的两个对象散列值也相等===。
 */
public class EqualExample {
    private int x;
    private int y;
    private int z;

    public EqualExample(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //重写 equals方法
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        EqualExample that = (EqualExample) obj;
        if (x != that.x) return false;
        if (y != that.y) return false;
        return z == that.z;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    public static void main(String[] args) {
        EqualExample equalExample=new EqualExample(1,2,3);
        EqualExample equalExample2=new EqualExample(1,2,3);
        if(equalExample.equals(equalExample2)){
            System.out.println("相等");
        }else{
            System.out.println("不相等");
        }
    }
}
