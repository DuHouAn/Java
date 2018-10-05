package com.southeast.throwException;

/**
 * Created by 18351 on 2018/8/29.
 */
public class Code_08_Eaception {
    public static int quotient(int x, int y) throws MyException { // 定义方法抛出异常
        if (y < 0) { // 判断参数是否小于0
            throw new MyException("除数不能是负数"); // 异常信息
            //这里是 MyException类型的对象 使用了有参构造方法
        }
        return x/y; // 返回值
    }

    public static void main(String[] args) {
        int a=1;
        int b=-1;
        try{
            quotient(a,b);
        }catch (MyException e){
            System.out.println(e.getMessage());
        }catch (ArithmeticException e){
            System.out.println("除数不能为零");
        } catch (Exception e){
            System.out.println("程序发生了其他异常");
        }
    }
}

/*
* 异常链问题：
* 1) 如果调用quotient(3,-1)，将发生MyException异常，程序调转到catch (MyException e)代码块中执行；
* 2) 如果调用quotient(5,0)，将会因“除数为0”错误引发ArithmeticException异常，属于运行时异常类，由Java运行时系统自动抛出。
*   quotient（）方法没有捕捉ArithmeticException异常，
*   Java运行时系统将沿方法调用栈查到main方法，将抛出的异常上传至quotient（）方法的调用者。
*   Java这种向上传递异常信息的处理机制，形成异常链。
*
*   TODO：Java方法抛出的可查异常将依据调用栈、沿着方法调用的层次结构一直传递到具备处理能力的调用方法，最高层次到main方法为止。
*   TODO：如果异常传递到main方法，而main不具备处理能力，也没有通过throws声明抛出该异常，将可能出现编译错误。

*3)如还有其他异常发生，将使用catch (Exception e)捕捉异常。由于Exception是所有异常类的父类，如果将catch (Exception e)代码块放在其他两个代码块的前面，
* 后面的代码块将永远得不到执行，就没有什么意义了，所以catch语句的顺序不可调换。
*
* */