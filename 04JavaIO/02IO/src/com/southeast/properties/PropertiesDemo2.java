package com.southeast.properties;

import java.util.Properties;
import java.util.Set;

/**
 * Properties:属性集合类。
 *      是一个可以和IO流相结合使用的集合类。
 * Properties
 *      可保存在流中或从流中加载。属性列表中每个键及其对应值都是一个字符串。
 * 是Hashtable的子类，说明是一个Map集合。
 *
 *  特殊功能：
 * public Object setProperty(String key,String value)：添加元素
 * public String getProperty(String key):获取元素
 * public Set<String> stringPropertyNames():获取所有的键的集合
 */
public class PropertiesDemo2 {
    public static void main(String[] args) {
        Properties prop = new Properties();

        // 添加元素
        prop.setProperty("aaa","AAA");
        prop.setProperty("bbb","BBB");
        prop.setProperty("ccc","CCC");

        Set<String> set=prop.stringPropertyNames();
        for(String key:set){
            System.out.println(key+"=="+prop.getProperty(key));
        }
    }
}
