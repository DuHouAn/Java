package com.southeast.forInterview;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by 18351 on 2018/8/29.
 */
public class TestException7 {
    public static void main(String[] args) {
     /*   try {
            foo();
        } catch (IOException | JAXBException e) {
            e = new Exception("");
            //不能编译，因为在多个catch子句中的异常对象是不可变的，我们不能改变其值。
            //会得到这样的：The parameter e of a multi-catch block cannot be assigned编译时错误信息
            //删掉将e赋值给新异常对象这句来修正错误。
            e.printStackTrace();
        }catch(Exception e){
            e = new Exception("");
            e.printStackTrace();
        }*/
        try {
            foo();
        } catch (IOException | JAXBException e) {
            //e = new Exception("");//删掉将e赋值给新异常对象这句来修正错误。
            e.printStackTrace();
        }catch(Exception e){
            e = new Exception("");
            e.printStackTrace();
        }
    }

    public static void foo() throws IOException, JAXBException {
    }
}
