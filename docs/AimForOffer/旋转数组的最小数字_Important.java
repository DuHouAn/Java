import org.junit.Test;

/**
 * Created by 18351 on 2019/7/25.
 */
public class 旋转数组的最小数字_Important {
    //思路一：
    //遍历，遇到前一个数大于后一个数，就返回后一个数
    //时间复杂度是 O(n)
    public int minNumberInRotateArray_1(int [] array) {
        if(array==null || array.length==0){
            return 0;
        }
        if(array.length==1){
            return array[0];
        }
        //此时数组中至少有 2 个元素
        int res = array[0];
        for(int i=1;i<array.length;i++){
            if(array[i-1]>array[i]){
                res = array[i];
                break;
            }
        }
        return res;
    }

    //二分查找的变形：
    //关键在于确定旋转数组在那一部分
    public int minNumberInRotateArray(int [] array) {
        if(array==null || array.length==0){
            return 0;
        }
        if(array.length==1){
            return array[0];
        }

        int l=0;
        int h=array.length-1;
        while(l<h){
            int m = (h-l)/2+l;
            if(array[m]<=array[h]){ //[l,m] 是旋转数组
                h = m;
            }else{ //[m+1,h] 是旋转数组
                l =m+1;
            }
        }
        return array[l];
    }



    @Test
    public void test(){
        int[] nums={1,3,4,5};
        System.out.println(minNumberInRotateArray(nums));
    }
}
