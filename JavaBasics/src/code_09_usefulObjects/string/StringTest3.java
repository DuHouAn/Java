package code_09_usefulObjects.string;

/**
 * 统计大串中小串出现的次数
 * 举例：
 * 		在字符串"woaijavawozhenaijavawozhendeaijavawozhendehenaijavaxinbuxinwoaijavagun"
 * 结果：
 * 		java出现了5次
 *
 * 分析：
 * 		前提：是已经知道了大串和小串。
 * 		A:定义一个统计变量，初始化值是0
 * 		B:先在大串中查找一次小串第一次出现的位置 ( boolean contains(String str):判断大字符串中是否包含小字符串)
 * 			a:索引是-1，说明不存在了，就返回统计变量
 * 			b:索引不是-1，说明存在，统计变量++
 * 		C:把刚才的索引+小串的长度作为开始位置截取上一次的大串，返回一个新的字符串，并把该字符串的值重新赋值给大串
 * 		D:回到B
 */
public class StringTest3 {
    public static void main(String[] args) {
        String s1="woaijavawozhenaijavawozhendeaijavawozhendehenaijavaxinbuxinwoaijavagun";
        String s2="java";
        System.out.println(smallCount(s2,s1));
    }

    // 统计大串中小串出现的次数
    public static int smallCount(String smallStr,String bigStr) {
        int count=0;

        int index=bigStr.indexOf(smallStr);
        while(index!=-1){
            count++;
            //把刚才的索引+小串的长度作为开始位置截取上一次的大串
            int startIndex=index+smallStr.length();
            bigStr=bigStr.substring(startIndex);
            index=bigStr.indexOf(smallStr);
        }
        return count;
    }
}
