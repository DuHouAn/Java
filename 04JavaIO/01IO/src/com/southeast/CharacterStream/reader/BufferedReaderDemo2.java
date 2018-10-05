package com.southeast.CharacterStream.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by 18351 on 2018/9/2.
 */
public class BufferedReaderDemo2 {
    public static void main(String[] args) {
        BufferedReader br=null;
        try {
            br=new BufferedReader(new FileReader("a.txt"));

            //BufferedReader:字符缓冲输入流 一次课读取一行
            String line=null;
            while((line=br.readLine())!=null){
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br!=null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
