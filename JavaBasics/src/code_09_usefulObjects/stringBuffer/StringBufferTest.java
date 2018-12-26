package code_09_usefulObjects.stringBuffer;

/**
 * 把数组拼接成一个字符串
 */
public class StringBufferTest {
    public static void main(String[] args) {
        int[] arr={1,2,3,4};
        System.out.println(arrToString(arr));//[1,2,3,4]
    }

    public static String arrToString(int[] arr){
        StringBuffer buffer=new StringBuffer();
        buffer.append("[");
        for(int i=0;i<arr.length;i++){
            if(i==arr.length-1){
                buffer.append(arr[i]);
            }else{
                buffer.append(arr[i]).append(",");
            }
        }
        buffer.append("]");
        return buffer.toString();
    }
}
