package code_08_annotation.code_01;

public class Employee {
    @EmployeeName("AAA")
    private String employeeName;

    @EmployeeSex(sex = EmployeeSex.Sex.男)
    private String employeeSex;

    @Companay(companyId = 1,companayName = "BBB",companayAddress = "CCC")
    private String company;
}
