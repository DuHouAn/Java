import org.junit.Test;

/**
 * Created by 18351 on 2019/7/22.
 */
public class 替换空格_Important {
    public String replaceSpace(StringBuffer str) {
        //P1 指向原来的
        //令 P1 指向字符串原来的末尾位置，
        // P2 指向字符串现在的末尾位置。
        int P1 = str.length() - 1;
        // ' ' 要转化为 '%20' 是需要额外的 2 个字符的位置
        for(int i=0;i<=P1;i++){
            char ch = str.charAt(i);
            if(ch==' '){
                str.append("  ");
            }
        }
        //P1 和 P2 从后向前遍历，当 P1 遍历到一个空格时，
        //就需要令 P2 指向的位置依次填充 02%（注意是逆序的），否则就填充上 P1 指向字符的值。
        //从后向前遍是为了在改变 P2 所指向的内容时，不会影响到 P1 遍历原来字符串的内容。
        int P2=str.length()-1;
        while(P1>=0 && P2 >= P1){
            char ch = str.charAt(P1);
            if(ch==' '){
                str.setCharAt(P2--,'0');
                str.setCharAt(P2--,'2');
                str.setCharAt(P2--,'%');
            }else{
                str.setCharAt(P2--,ch);
            }
            P1--;
        }
        return str.toString();
    }

    @Test
    public void test(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("A B C");
        System.out.println(replaceSpace(buffer).toString());
    }
}
