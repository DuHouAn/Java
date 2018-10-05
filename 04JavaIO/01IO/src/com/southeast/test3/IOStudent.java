package com.southeast.test3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * 键盘录入3个学生信息(姓名,语文成绩,数学成绩,英语成绩),按照总分从高到低存入文本文件
 *
 * 分析：
 * 		A:创建学生类
 * 		B:创建集合对象
 * 			TreeSet<Student>
 * 		C:键盘录入学生信息存储到集合
 * 		D:遍历集合，把数据写到文本文件
 */
public class IOStudent {
    public static void main(String[] args) throws IOException {
        TreeSet<Student> set=new TreeSet<Student>(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                //语文成绩,数学成绩,英语成绩),按照总分从高到低存入文本文件
                //总分相同的话，按照语文成绩高的排在前
                int num=s2.getSum()-s1.getSum();
                int num2=(num==0)?s2.getChinese()-s1.getChinese():num;
                int num3=(num2==0)?s2.getMath()-s1.getMath():num2;
                int num4=(num3==0)?s2.getEnglish()-s1.getEnglish():num3;
                int num5=(num4==0)?s1.getName().compareTo(s2.getName()):num4;
                return num5;
            }
        });

        Student s1=new Student("aaa",78,92,100);
        Student s2=new Student("bbb",79,91,100);
        Student s3=new Student("ccc",78,90,100);

        set.add(s1);
        set.add(s2);
        set.add(s3);

        //遍历集合，把数据写到文本文件
        BufferedWriter bw=new BufferedWriter(new FileWriter("student.txt"));

        bw.write("姓名"+"\t语文"+"\t数学"+"\t英语"+"\t总分");
        bw.newLine();
        bw.flush();
        for(Student s:set){
            bw.write(s.getName()+"\t\t"+s.getChinese()+"\t\t"
                    +s.getMath()+"\t\t"+s.getEnglish()+"\t\t"+s.getSum());
            bw.newLine();
            bw.flush();
        }

        bw.close();
    }
}
