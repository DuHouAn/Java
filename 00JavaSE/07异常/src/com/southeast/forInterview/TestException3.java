package com.southeast.forInterview;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * Created by 18351 on 2018/8/29.
 */
public class TestException3 {
    public static void main(String[] args) {
        try {
            foo();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(JAXBException e){
            //同样不能编译，因为JAXBException是个受检查的异常，而foo方法应该抛出此异常供调用方法捕获
            //会得到：Unreachable catch block for JAXBException这样的错误信息。
            //注意：受检查的异常适用于那些不是因程序引起的错误情况
            e.printStackTrace();
        }catch(NullPointerException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //JAXBException是个受检查的异常，而foo方法应该抛出此异常供调用方法捕获
   /* public static void foo() throws IOException{

    }*/

    public static void foo() throws IOException,JAXBException{

    }
}
