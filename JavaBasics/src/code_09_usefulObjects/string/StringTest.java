package code_09_usefulObjects.string;

/**
 * 需求：把一个字符串的首字母转成大写，其余为小写。(只考虑英文大小写字母字符)
 * 举例：
 * 		helloWORLD
 * 结果：
 * 		Helloworld
 *
 * 		A:先获取第一个字符
 * 		B:获取除了第一个字符以外的字符
 * 		C:把A转成大写
 * 		D:把B转成小写
 * 		E:C拼接D
 */
public class StringTest {
    public static void main(String[] args) {
        String str="helloWORLD";
        str=str.substring(0,1).toUpperCase().
                concat(str.substring(1).toLowerCase());
        System.out.println(str);
    }
}
