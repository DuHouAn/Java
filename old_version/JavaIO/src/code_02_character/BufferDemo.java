package code_02_character;

import java.io.*;

/**
 * 字符缓冲流的特殊方法：
 * BufferedWriter:
 * 		public void newLine():根据系统来决定换行符
 * BufferedReader:
 * 		public String readLine()：一次读取一行数据
 * 		包含该行内容的字符串，不包含任何行终止符，如果已到达流末尾，则返回 null
 */
public class BufferDemo {
    public static void main(String[] args) throws IOException {
        //write();
        read();
    }

    public static void write() throws IOException {
        BufferedWriter bw=new BufferedWriter(new FileWriter("demo1.txt"));

        for (int x = 0; x < 3; x++) {
            bw.write("我爱林青霞");
            bw.newLine();
            bw.flush();
        }
        bw.write("重要的事情说三遍");

        bw.close();
    }

    public static void read() throws IOException {
        BufferedReader br=new BufferedReader(new FileReader("demo1.txt"));

        String line=null;
        while((line=br.readLine())!=null){
            System.out.println(line);
        }

        br.close();
    }
}
