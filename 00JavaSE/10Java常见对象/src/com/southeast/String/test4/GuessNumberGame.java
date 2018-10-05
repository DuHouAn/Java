package com.southeast.String.test4;

import java.util.Scanner;

/**
 * 概猜数字小游戏
 * 这是一个工具类
 */
public class GuessNumberGame {
    private GuessNumberGame() {//构造方法已经私有了
    }

    public static void start(){ //start()是静态方法
        // 产生一个随机数
        int number = (int) (Math.random() * 100) + 1;//该随机数在 [1,100]之间
        while (true) {
            // 键盘录入数据
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入你要猜的数据(1-100)：");
            int guessNumber = sc.nextInt();

            // 判断
            if (guessNumber > number) {
                System.out.println("你猜的数据" + guessNumber + "大了");
            } else if (guessNumber < number) {
                System.out.println("你猜的数据" + guessNumber + "小了");
            } else {
                System.out.println("恭喜你，猜中了");
                break;
            }
        }
    }
}
