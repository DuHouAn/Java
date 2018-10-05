package com.southeast.catchException.tryCatchFinally;

/**
 *3.
 * try-catch-finally 规则(异常处理语句的语法规则）：
 1)  必须在 try 之后添加 catch 或 finally 块。
        try 块后可同时接 catch 和 finally 块，但至少有一个块。
 2) 必须遵循块顺序：若代码同时使用 catch 和 finally 块，则必须将 catch 块放在 try 块之后。
 3) catch 块与相应的异常类的类型相关。
 4) 一个 try 块可能有多个 catch 块。
        若如此，则执行第一个匹配块。
        即Java虚拟机会把实际抛出的异常对象依次和各个catch代码块声明的异常类型匹配，如果异常对象为某个异常类型或其子类的实例，就执行这个catch代码块，不会再执行其他的 catch代码块
 5) 可嵌套 try-catch-finally 结构。
 6) 在 try-catch-finally 结构中，可重新抛出异常。
 7) 除了下列情况，总将执行 finally 做为结束：
        JVM 过早终止（调用 System.exit(int)）；
        在 finally 块中抛出一个未处理的异常；
        计算机断电、失火、或遭遇病毒攻击。
 */
public class Code_10_Exception {
    public static void main(String[] args) {

    }
}
