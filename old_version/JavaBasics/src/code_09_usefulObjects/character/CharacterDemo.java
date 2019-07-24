package code_09_usefulObjects.character;

/**
 *  public static boolean isUpperCase(char ch):
 *      判断给定的字符是否是大写字符
 * public static boolean isLowerCase(char ch):
 *      判断给定的字符是否是小写字符
 * public static boolean isDigit(char ch):
 *      判断给定的字符是否是数字字符
 * public static char toUpperCase(char ch):
 *      把给定的字符转换为大写字符
 * public static char toLowerCase(char ch):
 *      把给定的字符转换为小写字符
 */
public class CharacterDemo {
    public static void main(String[] args) {
        // public static boolean isUpperCase(char ch):判断给定的字符是否是大写字符
        System.out.println("isUpperCase:" + Character.isUpperCase('A'));//true
        System.out.println("isUpperCase:" + Character.isUpperCase('a'));//false
        System.out.println("isUpperCase:" + Character.isUpperCase('0'));//false
        System.out.println("-----------------------------------------");

        // public static boolean isLowerCase(char ch):判断给定的字符是否是小写字符
        System.out.println("isLowerCase:" + Character.isLowerCase('A'));//false
        System.out.println("isLowerCase:" + Character.isLowerCase('a'));//true
        System.out.println("isLowerCase:" + Character.isLowerCase('0'));//false
        System.out.println("-----------------------------------------");

        // public static boolean isDigit(char ch):判断给定的字符是否是数字字符
        System.out.println("isDigit:" + Character.isDigit('A'));//false
        System.out.println("isDigit:" + Character.isDigit('a'));//false
        System.out.println("isDigit:" + Character.isDigit('0'));//true
        System.out.println("-----------------------------------------");

        // public static char toUpperCase(char ch):把给定的字符转换为大写字符
        System.out.println("toUpperCase:" + Character.toUpperCase('A'));//A
        System.out.println("toUpperCase:" + Character.toUpperCase('a'));//A
        System.out.println("-----------------------------------------");

        // public static char toLowerCase(char ch):把给定的字符转换为小写字符
        System.out.println("toLowerCase:" + Character.toLowerCase('A'));//a
        System.out.println("toLowerCase:" + Character.toLowerCase('a'));//a
    }
}
