package com.southeast.accessAuthority;

public class AccessWithinnerClassExample {
    private class InnerClass{ //内部类
        int x;
    }

    private InnerClass innerClass;

    public AccessWithinnerClassExample() {
        this.innerClass = new  InnerClass();
    }

    public int getValue(){
        return innerClass.x;
    }
}
