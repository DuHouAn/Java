package com.southeast.forInterview;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 18351 on 2018/8/29.
 */
public class TestException2 {
    public static void main(String[] args) {
       /* try {
            go();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            //代码将不能编译，因为FileNotFoundException是IOException的子类，所以，FileNotFoundException的catch子句将被隐藏，
            //同时，你会：Unreachable catch block for FileNotFoundException.的错误信息。因为异常已被IOException的catch子句处理。
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }*/
        try {
            go();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void go() throws IOException, JAXBException, FileNotFoundException {
    }
}
