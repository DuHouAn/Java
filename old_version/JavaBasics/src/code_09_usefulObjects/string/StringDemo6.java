package code_09_usefulObjects.string;

/**
 *  * String类的其他功能：
 *
 * 替换功能：
 * String replace(char old,char new)
 * String replace(String old,String new)
 *
 * 去除字符串两空格
 * String trim()
 *
 * 按字典顺序比较两个字符串
 * int compareTo(String str)
 * int compareToIgnoreCase(String str)
 */
public class StringDemo6 {
    public static void main(String[] args) {
        // 替换功能
        String s1 = "helloworld";
        String s2 = s1.replace('l', 'k');
        String s3 = s1.replace("owo", "ak47");
        System.out.println("s1:" + s1);
        System.out.println("s2:" + s2);
        System.out.println("s3:" + s3);
        System.out.println("---------------");

        // 去除字符串两空格
        String s4 = " hello world  ";
        String s5 = s4.trim();
        System.out.println("s4:" + s4 + "---");
        System.out.println("s5:" + s5 + "---");

        // 按字典顺序比较两个字符串
        String s6 = "hello";
        String s7 = "hello";
        String s8 = "abc";
        String s9 = "xyz";
        System.out.println(s6.compareTo(s7));// 0
        System.out.println(s6.compareTo(s8));// 7
        System.out.println(s6.compareTo(s9));// -16
    }
}
