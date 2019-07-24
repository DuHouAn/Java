package code_09_usefulObjects.stringBuffer;

/**
 * * StringBuffer的截取功能:注意返回值类型不再是StringBuffer本身了
 * public String substring(int start) //TODO：注意截取返回的是String,而不是StringBuffer了
 * public String substring(int start,int end)
 */
public class StringBufferDemo7 {
    public static void main(String[] args) {
        String s="hello";
        System.out.println(stringToStringBuffer(s)); //hello
        System.out.println(stringBufferToString(stringToStringBuffer(s)));//hello
    }

    // String -->StringBuffer
    public static StringBuffer stringToStringBuffer(String s) {
        // 注意：不能把字符串的值直接赋值给StringBuffer
        // StringBuffer sb = "hello";
        // StringBuffer sb = s;

        // 方式1:通过构造方法
      /*  StringBuffer sb = new StringBuffer(s);
        return sb;*/
        // 方式2：通过append()方法
        StringBuffer sb2 = new StringBuffer();
        sb2.append(s);
        return sb2;
    }

    //StringBuffer -->String
    public static String stringBufferToString(StringBuffer buffer){
        // 方式1:通过构造方法
       /* String str = new String(buffer);
        return str;*/
        // 方式2：通过toString()方法
        String str2 = buffer.toString();
        return str2;
    }
}
