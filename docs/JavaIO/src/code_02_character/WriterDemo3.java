package code_02_character;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by 18351 on 2019/1/5.
 */
public class WriterDemo3 {
    public static void main(String[] args) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("bw.txt"));

        bw.write("hello");
        bw.write("world");
        bw.write("java");
        bw.flush();

        bw.close();
    }
}