package com.southeast.test2;

import java.io.*;

/**
 * 需求：复制单级文件夹
 *
 * 数据源：copy\\copy.mp3
 * 目的地：copytest\\copy.mp3
 *
 * 分析：
 * 		A:封装目录
 * 		B:获取该目录下的所有文本的File数组
 * 		C:遍历该File数组，得到每一个File对象
 * 		D:把该File进行复制
 */
public class IOCopy {
    public static void main(String[] args) throws IOException {
        File srcFile=new File("copy");
        File destFile=new File("copyTest");

        //如果目的文件夹不存在，就创建新的文件夹
        if(!destFile.exists()){
            destFile.mkdir();
        }

        File[] listFiles= srcFile.listFiles();//得到该单级文件夹下的所有文件
        if(listFiles!=null){
            for(File file:listFiles){
                String name=file.getName();
                File newFile=new File(destFile,name);
                copyFile(file,newFile);
            }
        }
    }

    //复制文件
    //思考 --> 使用 字节输入流和字节输出流
    // BufferedInputStream
    // BufferedOutputStream
    public static void copyFile(File srcFile,File destFile) throws IOException{
        BufferedInputStream bis=new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(destFile));

        byte[] bys=new byte[1024];
        int len=0;
        while((len=bis.read(bys))!=-1){
            bos.write(bys,0,len);
        }

        bis.close();
        bos.close();
    }
}
