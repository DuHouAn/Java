package code_02_character;

import java.io.*;

/**
 * Created by 18351 on 2019/1/5.
 */
public class CopyFile {
    public static void main(String[] args) throws IOException{
        String srcFile="pride-and-prejudice.txt";
        String destFile="demo5.txt";
        long start=System.currentTimeMillis();
        //method(srcFile,destFile);//共耗时191毫秒
        //method2(srcFile,destFile);//共耗时59毫秒
        //method3(srcFile,destFile);//共耗时68毫秒
        //method4(srcFile,destFile);//共耗时38毫秒
        method5(srcFile,destFile);//共耗时177毫秒
        long end=System.currentTimeMillis();
        System.out.println("共耗时"+(end-start)+"毫秒");
    }

    //基本字符流一次读写一个字符
    public static void method(String srcFile,String destFile) throws IOException{
        FileReader fr=new FileReader(srcFile);
        FileWriter fw=new FileWriter(destFile);

        int ch=0;
        while((ch=fr.read())!=-1){
            fw.write(ch);
        }

        fr.close();
        fw.close();
    }

    //基本字符流一次读写一个字符数组
    public static void method2(String srcFile,String destFile) throws IOException{
        FileReader fr=new FileReader(srcFile);
        FileWriter fw=new FileWriter(destFile);

        int len=0;
        char[] chs=new char[1024];
        while((len=fr.read(chs))!=-1){
            fw.write(chs,0,len);
        }

        fr.close();
        fw.close();
    }

    //字符缓冲流一次读写一个字符
    public static void method3(String srcFile,String destFile) throws IOException{
        BufferedReader br=new BufferedReader(new FileReader(srcFile));
        BufferedWriter bw=new BufferedWriter(new FileWriter(destFile));

        int ch=0;
        while((ch=br.read())!=-1){
            bw.write(ch);
        }

        br.close();
        bw.close();
    }

    //字符缓冲流一次读写一个字符数组
    public static void method4(String srcFile,String destFile) throws IOException{
        BufferedReader br=new BufferedReader(new FileReader(srcFile));
        BufferedWriter bw=new BufferedWriter(new FileWriter(destFile));

        int len=0;
        char[] chs=new char[1024];
        while((len=br.read(chs))!=-1){
            bw.write(chs,0,len);
        }

        br.close();
        bw.close();
    }

    public static void method5(String srcFile,String destFile) throws IOException{
        BufferedReader br=new BufferedReader(new FileReader(srcFile));
        BufferedWriter bw=new BufferedWriter(new FileWriter(destFile));

        String line=null;
        while((line=br.readLine())!=null){
            bw.write(line);
            bw.newLine();
            bw.flush();
        }

        br.close();
        bw.close();
    }
}
