package com.southeast.properties;

import java.io.*;
import java.util.Properties;
import java.util.Set;

/**
 * 我有一个猜数字小游戏的程序，
 * 请写一个程序实现在测试类中只能用5次，超过5次提示：游戏试玩已结束，请付费。
 */
public class PropertiesTest2 {
    public static void main(String[] args) throws IOException {
        Properties prop=new Properties();

        //将数据加载到集合中
        FileReader fr=new FileReader("properties\\count.txt");
        prop.load(fr);
        fr.close();

        String countstr=prop.getProperty("count");
        int number= Integer.parseInt(countstr);

        if (number > 5) {
            System.out.println("游戏试玩已结束，请付费。");
            System.exit(0);
        } else {
            number++;
            prop.setProperty("count", String.valueOf(number));

            //再将数据放入count.txt中
            Writer w = new FileWriter("properties\\count.txt");
            prop.store(w, null);
            w.close();

            GuessNumber.start();
        }
    }
}
