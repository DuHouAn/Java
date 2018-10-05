package com.southeast.test5;

import java.io.FileReader;
import java.io.IOException;

/**
 * Created by DHA on 2018/9/3.
 */
public class MyBufferedReaderDemo {
    public static void main(String[] args) throws IOException {
        MyBufferedReader reader=new MyBufferedReader(new FileReader("test5\\a.txt"));

        String line=null;
        while((line=reader.readLine())!=null){
            System.out.println(line);
        }

        reader.close();
    }
}
