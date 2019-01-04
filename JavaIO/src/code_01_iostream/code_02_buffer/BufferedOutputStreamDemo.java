package code_01_iostream.code_02_buffer;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 18351 on 2019/1/4.
 */
public class BufferedOutputStreamDemo {
    public static void main(String[] args) throws IOException {
        BufferedOutputStream bos=new
                BufferedOutputStream(new FileOutputStream("demo1.txt"));
        bos.write("hello".getBytes());
        bos.close();
    }
}
