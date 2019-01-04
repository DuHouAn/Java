package code_01_iostream.code_00_fileoutputstream;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 18351 on 2019/1/4.
 */
public class OutputStreamDemo2 {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = new FileOutputStream("demo2.txt");

        // 调用write()方法
        fos.write(97); //97 -- 底层二进制数据	-- 通过记事本打开 -- 找97对应的字符值 -- a

        //public void write(byte[] b):写一个字节数组
        byte[] bys={98,99,100,101};
        fos.write(bys);

        //public void write(byte[] b,int off,int len):写一个字节数组的一部分
        fos.write(bys,1,3);

        //释放资源
        fos.close();
    }
}
