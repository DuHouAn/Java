package code_09_usefulObjects.character;

import java.util.Scanner;

/**
 *  统计一个字符串中大写字母字符，小写字母字符，数字字符出现的次数。(不考虑其他字符)
 *
 * 分析：
 * 		A:定义三个统计变量。
 * 			int bigCont=0;
 * 			int smalCount=0;
 * 			int numberCount=0;
 * 		B:键盘录入一个字符串。
 * 		C:把字符串转换为字符数组。
 * 		D:遍历字符数组获取到每一个字符
 * 		E:判断该字符是
 * 			大写	bigCount++;
 * 			小写	smalCount++;
 * 			数字	numberCount++;
 * 		F:输出结果即可
 */
public class CharacterTest {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();
        printCount(str);
        printCount2(str);
    }

    //原来的写法
    public static void printCount(String str) {
        int numberCount=0;
        int lowercaseCount=0;
        int upercaseCount=0;

        for(int index=0;index<str.length();index++){
            char ch=str.charAt(index);
            if(ch>='0' && ch<='9'){
                numberCount++;
            }else if(ch>='A' && ch<='Z'){
                upercaseCount++;
            }else if(ch>='a' && ch<='z'){
                lowercaseCount++;
            }
        }
        System.out.println("数字有"+numberCount+"个");
        System.out.println("小写字母有"+lowercaseCount+"个");
        System.out.println("大写字母有"+upercaseCount+"个");
    }

    //使用包装类来改进
    public static void printCount2(String str) {
        int numberCount=0;
        int lowercaseCount=0;
        int upercaseCount=0;

        for(int index=0;index<str.length();index++){
            char ch=str.charAt(index);
            if(Character.isDigit(ch)){
                numberCount++;
            }else if(Character.isUpperCase(ch)){
                upercaseCount++;
            }else if(Character.isLowerCase(ch)){
                lowercaseCount++;
            }
        }
        System.out.println("数字有"+numberCount+"个");
        System.out.println("小写字母有"+lowercaseCount+"个");
        System.out.println("大写字母有"+upercaseCount+"个");
    }
}