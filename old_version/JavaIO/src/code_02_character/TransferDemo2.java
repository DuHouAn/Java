package code_02_character;

import java.io.*;

/**
 * Created by 18351 on 2019/1/5.
 */
public class TransferDemo2 {
    public static void main(String[] args) throws IOException {
        // 封装数据源
        BufferedReader br = new BufferedReader(new FileReader("demo1.txt"));
        // 封装目的地
        BufferedWriter bw = new BufferedWriter(new FileWriter("demo4.txt"));

        String line=null;
        while((line=br.readLine())!=null){
            bw.write(line);
            bw.newLine();
            bw.flush();
        }

        // 释放资源
        bw.close();
        br.close();
    }
}
