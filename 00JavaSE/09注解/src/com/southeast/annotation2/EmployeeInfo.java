package com.southeast.annotation2;

public class EmployeeInfo {
    @EmployeeName("杜厚安")
    private String employeeName;

    @EmployeeSex(sex = EmployeeSex.Sex.男)
    private String employeeSex;

    @Companay(companyId = 1,companayName = "南软",companayAddress = "南京雨花台区")
    private String company;
}
