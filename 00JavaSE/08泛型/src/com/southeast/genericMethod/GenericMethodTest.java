package com.southeast.genericMethod;

public class GenericMethodTest {
    public static void main(String[] args) {
        Pair<Integer,String> p1=new Pair<Integer,String>(1,"Apple");
        //这种显示 是 java1.7/java1.8利用type reference,让java自动推导出相应的参数类型。
        Pair<Integer,String> p2=new Pair<Integer,String>(1,"Pair");
        boolean isSame=Utils.compare(p1,p2);
        System.out.println(isSame);
    }
}
