package com.southeast.test6;

import com.southeast.test5.MyBufferedReader;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by DHA on 2018/9/3.
 * TODO:十分重要的编程思想-->通过继承来扩展功能
 */
public class MyLineNumberReader2 extends MyBufferedReader{
    private int lineNumber=0;//设置行号的初始值为0,这样每读取一行就加1

    public MyLineNumberReader2(Reader r) {
       super(r);
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    @Override
    public String readLine() throws IOException{
        lineNumber++;//每次读取一行，就加一行
        return super.readLine();
    }

    @Override
    public void close() throws IOException {
        super.close();
    }
}
