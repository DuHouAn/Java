package com.southeast.test6;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * Created by DHA on 2018/9/3.
 */
public class LineNumberReaderDemo {
    public static void main(String[] args) throws IOException {
        LineNumberReader lnr=new LineNumberReader(new FileReader("test6\\a.txt"));

        String line=null;
        while((line=lnr.readLine())!=null){
            System.out.println(lnr.getLineNumber()+":"+line);
        }

        lnr.close();
    }
}
