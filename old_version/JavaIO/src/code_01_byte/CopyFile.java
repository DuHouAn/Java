package code_01_byte;

import java.io.*;

/**
 * 使用字节流对象复制文件
 */
public class CopyFile {
    public static void main(String[] args) throws IOException {
        String strFile="国旗歌.mp4";
        String destFile="demo3.mp4";
        long start = System.currentTimeMillis();
        //copyFile(strFile,destFile); //共耗时：75309毫秒
        //copyFile2(strFile,destFile); //共耗时：153毫秒
        //copyFile3(strFile,destFile);//共耗时：282毫秒
        copyFile4(strFile,destFile);//共耗时：44毫秒
        long end = System.currentTimeMillis();
        System.out.println("共耗时：" + (end - start) + "毫秒");
    }

    /**
     * 基本字节流一次读写一个字节
     */
    public static void copyFile(String srcFile,String destFile) throws IOException {
        FileInputStream fis=new FileInputStream(srcFile);
        FileOutputStream fos=new FileOutputStream(destFile);

        int by=0;
        while((by=fis.read())!=-1){
            fos.write(by);
        }

        fis.close();
        fos.close();
    }

    /**
     * 基本字节流一次读写一个字节数组
     */
    public static void copyFile2(String srcFile,String destFile) throws IOException{
        FileInputStream fis=new FileInputStream(srcFile);
        FileOutputStream fos=new FileOutputStream(destFile);

        int len=0;
        byte[] bys=new byte[1024];
        while((len=fis.read(bys))!=-1){
            fos.write(bys,0,len);
        }

        fis.close();
        fos.close();
    }

    /**
     * 高效字节流一次读写一个字节
     */
    public static void copyFile3(String srcFile,String destFile) throws IOException{
        BufferedInputStream bis=new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(destFile));

        int by=0;
        while((by=bis.read())!=-1){
            bos.write(by);
        }

        bis.close();
        bos.close();
    }

    /**
     * 高效字节流一次读写一个字节数组
     */
    public static void copyFile4(String srcFile,String destFile) throws IOException{
        BufferedInputStream bis=new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(destFile));

        int len=0;
        byte[] bys=new byte[1024];
        while((len=bis.read(bys))!=-1){
            bos.write(bys,0,len);
        }

        bis.close();
        bos.close();
    }
}
