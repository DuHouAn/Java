package com.southeast.test5;

import java.io.IOError;
import java.io.IOException;
import java.io.Reader;

/**
 *  用Reader模拟BufferedReader的readLine()功能
 *
 * readLine():一次读取一行，根据换行符判断是否结束，只返回内容，不返回换行符
 */
public class MyBufferedReader {
    private Reader reader;

    public MyBufferedReader(Reader reader) {
        this.reader=reader;
    }

    //readLine():一次读取一行，根据换行符判断是否结束，只返回内容，不返回换行符
    /**
     * 思路：
     * 两个读取方法，一次读取一个字符或者一次读取一个字符数组
     * 我们很容易想到字符数组比较好，但是问题来了，就是这个数组的长度不确定。所以，只能选择一次读取一个字符。
     * 我们再读取下一个字符的时候，上一个字符就丢失了 所以，我们又应该定义一个临时存储空间，把读取过的字符给存储起来。
     */
    public String readLine() throws IOException{
       /*
        //读取整个文件
       int ch=0;
        while((ch=reader.read())!=-1){
            System.out.print((char)ch);
        }*/

       StringBuffer sb=new StringBuffer();//定义一个临时存储空间，把读取过的字符给存储起来
        //读取一行
        int ch=0;
        while((ch=reader.read())!=-1){ //Windeows中每行结尾换行是 \r\n
            if(ch=='\r'){
                continue;
            }
            if(ch=='\n'){ //表示到文件末尾
                return sb.toString();
            }else{
                sb.append((char)ch);
            }
        }

        //因为最后一行有可能没有换行符 即\r\n 文件就结束了，此时判断条件仍然是 \n,就会发生数据丢失
        //为防止最后一行数据丢失(因为最后一行数据无法return)，直接return数据即可
        if(sb.length()>0){
            return sb.toString();
        }
        return null;
    }

    public void close() throws IOException {
        reader.close();
    }
}
