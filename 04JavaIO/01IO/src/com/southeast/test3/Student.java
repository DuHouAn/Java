package com.southeast.test3;

/**
 * Created by DHA on 2018/9/3.
 */
public class Student {
    // 姓名
    private String name;
    // 语文成绩
    private int chinese;
    // 数学成绩
    private int math;
    // 英语成绩
    private int english;

    public Student() {
        super();
    }

    public Student(String name, int chinese, int math, int english) {
        super();
        this.name = name;
        this.chinese = chinese;
        this.math = math;
        this.english = english;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChinese() {
        return chinese;
    }

    public void setChinese(int chinese) {
        this.chinese = chinese;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getSum() {
        return this.chinese + this.math + this.english;
    }
}
