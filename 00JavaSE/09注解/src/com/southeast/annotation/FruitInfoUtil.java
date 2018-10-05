package com.southeast.annotation;

import java.lang.reflect.Field;

public class FruitInfoUtil {
    public static void getFruitInfo(Class<?> clazz){
        String strFruitName=" 水果名称：";
        String strFruitColor=" 水果颜色：";
        String strFruitProvider="供应商信息：";

        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            if(field.isAnnotationPresent(FruitName.class)){ //判断注解是不是 FruitName
                FruitName fruitName=field.getAnnotation(FruitName.class);
                strFruitName=strFruitName+fruitName.fruitName();
                System.out.println(strFruitName);
            }else if(field.isAnnotationPresent(FruitColor.class)){
                FruitColor fruitColor=field.getAnnotation(FruitColor.class);
                strFruitColor=strFruitColor+fruitColor.fruitColor().toString();
                System.out.println(strFruitColor);
            }else if(field.isAnnotationPresent(FruitProvider.class)){
                FruitProvider fruitProvider=field.getAnnotation(FruitProvider.class);
                strFruitProvider=strFruitProvider
                        + "[ 供应商编号："+fruitProvider.id()
                        +" 供应商名称：" +fruitProvider.name()
                        +" 供应商地址："+fruitProvider.address()+"]";
                System.out.println(strFruitProvider);
            }
        }
    }
}
