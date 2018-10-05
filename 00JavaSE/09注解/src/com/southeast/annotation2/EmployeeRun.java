package com.southeast.annotation2;

import java.util.Map;

public class EmployeeRun {
    public static void main(String[] args) {
       Map info=EmployeeInfoUtil.getEmployeeInfo(EmployeeInfo.class);
        System.out.println(info);
    }
}
