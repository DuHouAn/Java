package code_01_iostream.code_00_fileoutputstream;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 18351 on 2019/1/4.
 */
public class OutputStreamDemo3 {
    public static void main(String[] args) throws IOException {
        // 创建一个向具有指定 name 的文件中写入数据的输出文件流。
        // 如果第二个参数为 true，则将字节写入文件末尾处，而不是写入文件开始处。
        FileOutputStream fos = new FileOutputStream("demo2.txt",true);

        // 写数据
        for (int x = 0; x < 10; x++) {
            fos.write(("hello" + x).getBytes());
            fos.write("\r\n".getBytes());
        }

        //释放资源
        fos.close();
    }
}
