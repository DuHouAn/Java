package com.southeast.catchException.tryCatch;

/**
 * 注意：
 * （1）异常能明确的尽量明确，尽量不要用Exception处理
 * （2）Jdk1.7新特性中，异常是平级关系，前后无所谓，如果有父子关系，父在后。
 *
 * 例4  程序可能存在除数为0异常和数组下标越界异常。
 */
public class Code_03_Exception {
    public static void main(String[] args) {
        int[] intArray = new int[3];
        try {
            for (int i = 0; i <= intArray.length; i++) {
                intArray[i] = i;
                System.out.println("intArray[" + i + "] = " + intArray[i]);
                System.out.println("intArray[" + i + "]模 " + (i - 2) + "的值:  "
                        + intArray[i] % (i - 2));
            }
        } catch (ArrayIndexOutOfBoundsException e) { //捕捉ArrayIndexOutOfBoundsException
            System.out.println("intArray数组下标越界异常。");
        } catch (ArithmeticException e) {
            //捕捉ArithmeticException，如果捕捉到异常，处理结束后，就意味着整个try-catch结束。继续执行后续的代码
            System.out.println("除数为0异常。");
        }
        System.out.println("程序正常结束。");

        /*
        * 这里会先发生ArithmeticException。所以会执行 System.out.println("除数为0异常。");
        * TODO：一旦某个catch捕获到匹配的异常类型，将进入异常处理代码。
        * TODO：一经处理结束，就意味着整个try-catch语句结束。其他的catch子句不再有匹配和捕获异常类型的机会。
        * TODO：程序继承执行后续的代码
        * */
        /*
        * RuntimeException异常类包括运行时各种常见的异常，
        * ArithmeticException类和ArrayIndexOutOfBoundsException类都是它的子类。
        * 因此，RuntimeException异常类的catch子句应该放在最后面，
        * 否则可能会屏蔽其后的特定异常处理或引起编译错误。
        * */
    }
}
