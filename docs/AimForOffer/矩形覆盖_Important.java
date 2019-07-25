import org.junit.Test;

import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 18351 on 2019/7/23.
 */
public class 矩形覆盖_Important {
    //思路：
    //n=1 时，1 种
    //n=2 时，2 种
    //n=3 时，3 种
    //n=4 时，5 种
    //f(n) = f(n-1) + f(n-2)
    //注意：时间复杂度
    //使用递归的方式时间复杂度较高
    public int RectCover(int target) {
        if (target==1){
            return 1;
        }
        if(target==2){
            return 2;
        }

        //TODO:循环方式替代递归方式
        int pre1 = 1;
        int pre2 = 2;
        int res = 0;
        for(int i=3;i<=target;i++){
            res = pre1 + pre2;
            pre1 = pre2;
            pre2 = res;
        }
        return res;
    }
}
