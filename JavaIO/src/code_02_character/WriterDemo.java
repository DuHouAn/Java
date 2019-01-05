package code_02_character;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by 18351 on 2019/1/5.
 */
public class WriterDemo {
    public static void main(String[] args) throws IOException {
        OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream("demo1.txt"),"UTF-8");

        //直接写字符
        osw.write("你好 hello\r\n");

        osw.close();
    }
}
