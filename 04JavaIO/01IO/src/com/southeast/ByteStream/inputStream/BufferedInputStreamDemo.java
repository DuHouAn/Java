package com.southeast.ByteStream.inputStream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by 18351 on 2018/9/2.
 */
public class BufferedInputStreamDemo {
    public static void main(String[] args) {
        BufferedInputStream bis=null;
        try {
            bis=new BufferedInputStream(new FileInputStream("bos.txt"));

            byte[] bys=new byte[1024];
            int len=0;
            while((len=bis.read(bys))!=-1){
                System.out.println(new String(bys,0,len));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
