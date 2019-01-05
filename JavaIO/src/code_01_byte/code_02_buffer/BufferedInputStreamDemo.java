package code_01_byte.code_02_buffer;

import java.io.*;

/**
 * Created by 18351 on 2019/1/4.
 */
public class BufferedInputStreamDemo {
    public static void main(String[] args) throws IOException {
        BufferedInputStream bis=new
                BufferedInputStream(new FileInputStream("demo1.txt"));
        int by=-1;
        while((by=bis.read())!=-1){
            System.out.print((char)by);
        }
        bis.close();
    }
}
