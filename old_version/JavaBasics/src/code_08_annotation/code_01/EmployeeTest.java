package code_08_annotation.code_01;

import java.util.Map;

/**
 * Created by 18351 on 2018/12/25.
 */
public class EmployeeTest {
    public static void main(String[] args) {
        Map info=EmployeeInfoUtil.getEmployeeInfo(Employee.class);
        System.out.println(info);
    }
}
