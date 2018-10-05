package com.southeast.NIOTest;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * JDK7的之后的nio：
 * Path:路径
 * Paths:有一个静态方法返回一个路径
 * 		public static Path get(URI uri)
 * Files:提供了静态方法供我们使用
 * 		public static long copy(Path source,OutputStream out):复制文件
 * 		public static Path write(Path path,Iterable<? extends CharSequence> lines,Charset cs,OpenOption... options)
 */
public class NIODemo {
    public static void main(String[] args) throws IOException {
        ArrayList<String> list=new ArrayList<String>();

        list.add("aaa");
        list.add("bbb");
        list.add("ccc");

        //将 list数据存入指定文件中
        Files.write(Paths.get("c.txt"),list, Charset.defaultCharset());
    }
}
