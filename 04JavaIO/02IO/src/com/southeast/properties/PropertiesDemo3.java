package com.southeast.properties;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * public void load(Reader reader):
 *      把文件中的数据读取到集合中
 * public void store(Writer writer,String comments):
 *      把集合中的数据存储到文件
 */
public class PropertiesDemo3 {
    public static void main(String[] args) throws IOException {
        //myStore();
        myLoad();
    }

    public static void myLoad() throws IOException {
        Properties prop = new Properties();
        FileReader fr=new FileReader("properties\\fw.txt");
        prop.load(fr);

        Set<String> set=prop.stringPropertyNames();
        for(String key:set){
            System.out.println(key+"=="+prop.getProperty(key));
        }
        fr.close();
    }

    public static void myStore() throws IOException {
        Properties prop = new Properties();

        // 添加元素
        prop.setProperty("aaa","AAA");
        prop.setProperty("bbb","BBB");
        prop.setProperty("ccc","CCC");

        FileWriter fw=new FileWriter("properties\\fw.txt");
        prop.store(fw,"");
        fw.close();
    }
}
