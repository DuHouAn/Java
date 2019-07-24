package code_09_usefulObjects.stringBuffer;

/**
 *StringBuffer的反转功能：
 * public StringBuffer reverse()
 */
public class StringBufferDemo5 {
    public static void main(String[] args) {
        // 创建字符串缓冲区对象
        StringBuffer sb = new StringBuffer();

        // 添加数据
        sb.append("霞青林爱我");
        System.out.println("sb:" + sb);//霞青林爱我

        // public StringBuffer reverse()
        sb.reverse();
        System.out.println("sb:" + sb);//我爱林青霞
    }
}
