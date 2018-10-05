package com.southeast.switchTest;

/**
 * 从 Java 7 开始，可以在 switch 条件判断语句中使用 String 对象。
 * 注意：
 * switch 不支持 long，是因为 switch 的设计初衷是对那些只有少数的几个值进行等值判断，
 * 如果值过于复杂，那么还是用 if 比较合适。
 */
public class SwitchTest {
    public static void main(String[] args) {
        String s = "a";
        switch (s) {
            case "a":
                System.out.println("aaa");
                break;
            case "b":
                System.out.println("bbb");
                break;
        }
    }
}
