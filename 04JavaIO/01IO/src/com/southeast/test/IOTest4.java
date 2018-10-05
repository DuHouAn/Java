package com.southeast.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
* 需求：我有一个文本文件中存储了几个名字，请大家写一个程序实现随机获取一个人的名字。
*
* 分析：
* 		A:把文本文件中的数据存储到集合中
* 		B:随机产生一个索引
* 		C:根据该索引获取一个值
*/

public class IOTest4 {
    public static void main(String[] args) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader("iotest4.txt"));

        ArrayList<String> list=new ArrayList<String>();

        String line=null;
        while((line=br.readLine())!=null) {
            list.add(line);
        }

        Random random=new Random();
        int index=random.nextInt(list.size()); //在 [0,list.size()-1]之间获取随机下标
        System.out.println("选中的人是："+list.get(index));
        br.close();
    }
}
