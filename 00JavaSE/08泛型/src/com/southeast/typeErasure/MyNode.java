package com.southeast.typeErasure;

public class MyNode<T> extends Node<Integer>{
    public MyNode(Integer data) { super(data); }
    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }
}
