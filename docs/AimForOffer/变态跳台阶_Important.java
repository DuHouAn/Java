import org.junit.Test;

import java.util.Arrays;

/**
 * Created by 18351 on 2019/7/24.
 */
public class 变态跳台阶_Important {
    //动态规划思路：
    //dp[i] 表示跳 i 阶台阶共有 dp[i] 中跳法
    public int JumpFloorII(int target) {
        int[] dp=new int[target+1];
        //dp 数组数值初始化为 1
        Arrays.fill(dp,1);

        for(int i=2;i<=target;i++){
            for(int j=1;j<i;j++){  //每次青蛙可跳 j 步
                dp[i] = dp[i] + dp[j];
            }
        }
        return dp[target];
    }

    @Test
    public void test(){
        //int target = 3;
        //int target = 4;
        //int target = 5;
        int target = 6;
        System.out.println(JumpFloorII(target));
    }
}
