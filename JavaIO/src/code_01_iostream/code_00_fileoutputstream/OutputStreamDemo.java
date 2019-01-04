package code_01_iostream.code_00_fileoutputstream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by 18351 on 2019/1/4.
 */
public class OutputStreamDemo {
    public static void main(String[] args) throws IOException {
        OutputStream fos = new FileOutputStream("demo1.txt");
		/*
		 * 创建字节输出流对象了做了几件事情：
		 * A:调用系统功能去创建文件
		 * B:创建fos对象
		 * C:把fos对象指向这个文件
		 */

        //写数据
        fos.write("hello,IO".getBytes());
        fos.write("java".getBytes());

        //释放资源
        //关闭此文件输出流并释放与此流有关的所有系统资源。
        fos.close();
		/*
		 * 为什么一定要close()呢?
		 * A:让流对象变成垃圾，这样就可以被垃圾回收器回收了
		 * B:通知系统去释放跟该文件相关的资源
		 */
        //java.io.IOException: Stream Closed
        //fos.write("java".getBytes());
    }
}
