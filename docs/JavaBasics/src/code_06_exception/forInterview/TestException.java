package code_06_exception.forInterview;

/**
 * 看程序写出结果
 */
public class TestException {
    public static void main(String[] args) {
        System.out.println(getInt());
    }

    public static int getInt(){
        int a=10;
        try{
            System.out.println(a/0);
        }catch (ArithmeticException e){
            a=30;
            //return a; //将return a这一行注释掉，不难理解输出结果为40
            return a;
            //输出结果为30
            /**
             * 执行到这里的时候 这里的 return a 就是 return 30了，形成返回路径
             * 再执行finnally语句，这样 a=40
             * 再次回到以前的返回路径。return 30
             */
        }finally {
            a=40;
        }
        System.out.println("a="+a);
        return a;
    }
}
