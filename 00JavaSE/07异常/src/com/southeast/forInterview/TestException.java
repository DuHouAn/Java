package com.southeast.forInterview;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 18351 on 2018/8/29.
 */
public class TestException {
    public static void main(String[] args) {
           /* try{
            testExceptions();
        }catch ( FileNotFoundException | IOException e) {
            //上面这段代码将不能被编译，
            //因为multi-catch 语句中的替代无法通过子类化关联,java.io.FileNotFoundExceptionjava.io.IOException的子类
            e.printStackTrace();
        }*/
        try {
            testExceptions();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testExceptions() throws IOException,
            FileNotFoundException {
    }
}
