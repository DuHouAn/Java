package com.southeast.annotation2;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EmployeeInfoUtil {
    public static Map getEmployeeInfo(Class<?> clazz){
        HashMap<String,String> info=new HashMap<String,String>();
        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            if(field.isAnnotationPresent(EmployeeName.class)){
                EmployeeName employeeName=field.getAnnotation(EmployeeName.class);
                info.put("employeeName",employeeName.value());
            }else if(field.isAnnotationPresent(EmployeeSex.class)){
                EmployeeSex employeeSex=field.getAnnotation(EmployeeSex.class);
                info.put("emplyeeSex",employeeSex.sex().toString());
            }else if(field.isAnnotationPresent(Companay.class)){
                Companay companay=field.getAnnotation(Companay.class);
                info.put("company",companay.companyId()+":"+companay.companayName()+":"+companay.companayAddress());
            }
        }
        return info;
    }
}
