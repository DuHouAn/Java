package code_01_iostream.code_01_fileinputstream;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by 18351 on 2019/1/4.
 */
public class InputStreamDemo2 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis=new FileInputStream("demo1.txt");
        // 数组的长度一般是1024或者1024的整数倍
        byte[] bys = new byte[1024];
        int len = 0;
        while ((len = fis.read(bys)) != -1) {
            System.out.print(new String(bys, 0, len));
        }

        // 释放资源
        fis.close();
    }
}
