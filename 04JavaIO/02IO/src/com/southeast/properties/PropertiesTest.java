package com.southeast.properties;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

/**
 * /*
 * 我有一个文本文件(user.txt)，我知道数据是键值对形式的，但是不知道内容是什么。
 * 请写一个程序判断是否有“lisi”这样的键存在，如果有就改变其值为”100”
 *
 * 分析：
 * 		A:把文件中的数据加载到集合中
 * 		B:遍历集合，获取得到每一个键
 * 		C:判断键是否有为"lisi"的，如果有就修改其值为"100"
 * 		D:把集合中的数据重新存储到文件中
 */
public class PropertiesTest {
    public static void main(String[] args) throws IOException {
        Properties prop=new Properties();

        FileReader fr=new FileReader("properties\\user.txt");
        prop.load(fr);

        Set<String> set=prop.stringPropertyNames();
        for(String key:set){
            if("lisi".equals(key)){
                prop.setProperty(key,"100");
                break;
            }
        }

        FileWriter fw=new FileWriter("properties\\user.txt");
        prop.store(fw,"");
        fr.close();
        fw.close();
    }
}
