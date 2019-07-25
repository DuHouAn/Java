import org.junit.Test;

/**
 * Created by 18351 on 2019/7/24.
 */
public class 跳台阶 {
    //思路：target 是指 n 节级台阶
    //n = 1, 有 1 种跳法
    //n = 2, 有 2 种跳法
    //n = 3, 有 3 种跳法
    public int JumpFloor(int target) {
        if(target<=2){
            return target;
        }
        int pre1 = 1;
        int pre2 = 2;
        for(int i=3;i<=target;i++){
            int next = pre1 + pre2;
            pre1 = pre2;
            pre2 = next;
        }
        return pre2;
    }

    @Test
    public void test(){
        System.out.println(JumpFloor(5));
    }
}
