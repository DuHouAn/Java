package com.southeast.test6;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by DHA on 2018/9/3.
 */
public class MyLineNumberReaderDemo {
    public static void main(String[] args) throws IOException {
        //MyLineNumberReader reader=new MyLineNumberReader(new FileReader("test6\\a.txt"));
        MyLineNumberReader2 reader=new MyLineNumberReader2(new FileReader("test6\\a.txt"));

        String line=null;
        while((line=reader.readLine())!=null){
            System.out.println(reader.getLineNumber()+":"+line);
        }
        reader.close();
    }
}
