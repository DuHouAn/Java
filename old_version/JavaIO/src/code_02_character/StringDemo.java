package code_02_character;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 字符串的编码与解码：
 *  编码:把看得懂的变成看不懂的  String -- byte[]
 *  解码:把看不懂的变成看得懂的  byte[] -- String
 *
 *  - GBK 编码中，中文字符占 2 个字节，英文字符占 1 个字节；
 *  - UTF-8 编码中，中文字符占 3 个字节，英文字符占 1 个字节；
 *  - UTF-16be 编码中，中文字符和英文字符都占 2 个字节。
 */
public class StringDemo {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "你好";

        // String -- byte[]
        //byte[] bys = s.getBytes(); // [-28, -67, -96, -27, -91, -67]
        //byte[] bys = s.getBytes("GBK");// [-60, -29, -70, -61]
        //byte[] bys = s.getBytes("UTF-8");// [-28, -67, -96, -27, -91, -67]
        byte[] bys = s.getBytes("UTF-16be");//[79, 96, 89, 125]
        System.out.println(Arrays.toString(bys));

        // byte[] -- String
        //String ss = new String(bys); // 你好
        //String ss = new String(bys, "GBK"); // 你好
        //String ss = new String(bys, "UTF-8"); // 你好
        String ss = new String(bys, "UTF-16be"); //你好
        System.out.println(ss);
    }
}
