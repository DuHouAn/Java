package code_09_usefulObjects.stringBuffer;


/**
 * StringBuffer的替换功能：
 * public StringBuffer replace(int start,int end,String str):从start开始到end用str替换
 */
public class StringBufferDemo4 {
    public static void main(String[] args) {
        // 创建字符串缓冲区对象
        StringBuffer sb = new StringBuffer();

        // 添加数据
        sb.append("hello");
        sb.append("world");
        sb.append("java");
        System.out.println("sb:" + sb);

        // public StringBuffer replace(int start,int end,String str):从start开始到end用str替换
        // 需求：我要把world这个数据替换为"节日快乐"
        sb.replace(5,10,"节日快乐");
        System.out.println("sb："+sb);
    }
}
