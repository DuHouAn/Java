package code_01_byte.code_01_fileinputstream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 18351 on 2019/1/4.
 */
public class InputStreamDemo {
    public static void main(String[] args) throws IOException {
        InputStream fis=new FileInputStream("demo1.txt");
        int by = 0;
        // 读取，赋值，判断
        while ((by = fis.read()) != -1) {
            System.out.print((char) by);
        }

        // 释放资源
        fis.close();
    }
}