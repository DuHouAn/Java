package com.southeast.throwException;

/**
 1. throws抛出异常
 如果一个方法可能会出现异常，但没有能力处理这种异常，
 可以在方法声明处用throws子句来声明抛出异常。
 例如汽车在运行时可能会出现故障，汽车本身没办法处理这个故障，那就让开车的人来处理。

 throws语句用在方法定义时声明该方法要抛出的异常类型，
 如果抛出的是Exception异常类型，则该方法被声明为抛出所有的异常。
 多个异常可使用逗号分割。

 Throws抛出异常的规则：
 1) 如果是不可查异常（unchecked exception），即Error、RuntimeException或它们的子类，
 那么可以不使用throws关键字来声明要抛出的异常，编译仍能顺利通过，但在运行时会被系统抛出。

 2）必须声明方法可抛出的任何可查异常（checked exception）。
 即如果一个方法可能出现受可查异常，要么用try-catch语句捕获，要么用throws子句声明将它抛出，否则会导致编译错误。

 3)仅当抛出了异常，该方法的调用者才必须处理或者重新抛出该异常。
 当方法的调用者无力处理该异常的时候，应该继续抛出，而不是囫囵吞枣。

 4）调用方法必须遵循任何可查异常的处理和声明规则。
 若覆盖一个方法，则不能声明与覆盖方法不同的异常。
 声明的任何异常必须是被覆盖方法所声明异常的同类或子类。
 */
public class Code_07_Eaception {
    static void pop() throws NegativeArraySizeException { // throws抛出异常
        // 定义方法并抛出NegativeArraySizeException异常
        int[] arr = new int[-3]; // 创建数组
    }

    static void pop2(){ // throws抛出异常
        // 定义方法并抛出NegativeArraySizeException异常
        int[] arr = new int[-3]; // 创建数组
    }

    public static void main(String[] args) {
        try{
            pop2();
        }catch (NegativeArraySizeException e){
            System.out.println("pop()方法抛出的异常");// 输出异常信息
        }
        //pop(); //运行时异常，可不作处理，直接交给JVMchuli处理
    }
}