package com.southeast.test6;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by DHA on 2018/9/3.
 */
public class MyLineNumberReader {
    private Reader r;
    private int lineNumber=0;//设置行号的初始值为0,这样每读取一行就加1

    public MyLineNumberReader(Reader r) {
        this.r = r;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String readLine() throws IOException{
        lineNumber++;//每次读取一行，就加一行
        StringBuffer sb=new StringBuffer();

        int ch=0;
        while((ch=r.read())!=-1){
            if(ch=='\r'){
                continue;
            }
            if(ch=='\n'){
                return sb.toString();
            }else{
                sb.append((char)ch);
            }
        }
        //防止最后一行数据丢失
        if(sb.length()>0){
            return sb.toString();
        }
        return null;
    }

    public void close() throws IOException{
        r.close();
    }
}
