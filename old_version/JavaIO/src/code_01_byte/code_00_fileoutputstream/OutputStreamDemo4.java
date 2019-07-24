package code_01_byte.code_00_fileoutputstream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 18351 on 2019/1/4.
 */
public class OutputStreamDemo4 {
    public static void main(String[] args) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("demo1.txt");
            fos.write("java".getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 如果fos不是null，才需要close()
            if (fos != null) {
                // 为了保证close()一定会执行，就放到这里了
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
