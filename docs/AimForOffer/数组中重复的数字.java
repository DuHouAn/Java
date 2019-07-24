/**
 * Created by 18351 on 2019/7/23.
 */
public class 数组中重复的数字 {
    public boolean duplicate(int numbers[],int length,int [] duplication) {
        for(int i=0;i<length;i++){
            if(numbers[i]!=numbers[numbers[i]]){
                swap(numbers,i,numbers[i]);
                i--;
            }
        }

        for(int i=0;i<length;i++){
            if(numbers[i]!=i){
                duplication[0]=numbers[i];
                return true;
            }
        }
        return false;
    }

    private void swap(int[] nums,int i,int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
