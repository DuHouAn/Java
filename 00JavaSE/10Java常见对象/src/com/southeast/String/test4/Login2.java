package com.southeast.String.test4;

import java.util.Scanner;

/**
 * 在Login的基础上进行改进
 *
 * 模拟登录,给三次机会,并提示还有几次。如果登录成功，就可以玩猜数字小游戏了。
 *
 * 分析：
 * 		A:定义用户名和密码。已存在的。
 * 		B:键盘录入用户名和密码。
 * 		C:比较用户名和密码。
 * 			如果都相同，则登录成功
 * 			如果有一个不同，则登录失败
 * 		D:给三次机会，用循环改进，最好用for循环。
 */
public class Login2 {
    public static void main(String[] args) {
        String username="杜厚安";
        String password="123456";

        Scanner sc=new Scanner(System.in);

        for(int i=0;i<3;i++){
            System.out.println("请输入姓名:");
            String user=sc.nextLine();
            System.out.println("请输入密码:");
            String pwd=sc.nextLine();
            if(username.equals(user) && password.equals(pwd)){
                System.out.println("登录成功");
                GuessNumberGame.start();
                break;
            }else{
                if((2-i)==0){
                    System.out.println("登录失败");
                }else{
                    System.out.println("用户名或者密码错误，请再试一次，还有"+(2-i)+"次机会");
                }
            }
        }
    }
}
