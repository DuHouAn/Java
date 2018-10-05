package com.southeast.CharacterStream.reader;

import sun.swing.SwingUtilities2;

import java.io.*;

/**
 * Created by 18351 on 2018/9/2.
 */
public class BufferedReaderDemo {
    public static void main(String[] args) {
        BufferedReader br=null;
        try {
            br=new BufferedReader(new FileReader("a.txt"));

            //一次读取一个字符
          /*  int chs=0;
            while((chs=br.read())!=-1){
                System.out.print((char)chs);
            }*/
          char[] chs=new char[1024];
          int len=0;
          while((len=br.read(chs))!=-1){
              System.out.print(new String(chs,0,len));
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
