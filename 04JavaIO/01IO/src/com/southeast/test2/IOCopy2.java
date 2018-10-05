package com.southeast.test2;

import java.io.*;

/**
 *  需求：复制多几级文件夹
 *
 * 数据源：copy2\\aaa
 * 目的地：copy2Test
 *
 * 分析：
 * 		A:封装数据源File
 * 		B:封装目的地File
 * 		C:判断该File是文件夹还是文件
 * 			a:是文件夹
 * 				就在目的地目录下创建该文件夹
 * 				获取该File对象下的所有文件或者文件夹File对象
 * 				遍历得到每一个File对象
 * 				回到C
 * 			b:是文件
 * 				就复制(字节流)
 */
public class IOCopy2 {
    public static void main(String[] args) throws IOException {
        //封装数据源 就是 aaa目录，及其下的所有文件和目录
        File srcFile = new File("copy2\\aaa");
        // 封装目的地File
        File destFile = new File("copy2Test");
        if(!destFile.exists()){
            destFile.mkdir();
        }

        // 复制文件夹的功能
        copyFolder(srcFile, destFile);
    }

    //复制文件夹
    public static void copyFolder(File srcFile, File destFile) throws IOException {
        //先判断srcFile是否是文件夹-->遇到文件夹就创建，遇到文件就复制
        if(srcFile.isDirectory()){
            File newFolder=new File(destFile,srcFile.getName()); //创建新的文件夹
            newFolder.mkdir();

            //查看该文件夹下的文件
            File[] fileList=srcFile.listFiles();
            if(fileList!=null){
                for(File f:fileList){
                    copyFolder(f,newFolder);
                }
            }
        }else{//srcFile是文件，则在目标地下创建该文件
            File newFile=new File(destFile,srcFile.getName());
            copyFile(srcFile,newFile);
        }
    }

    //复制文件
    //思考 --> 使用 字节输入流和字节输出流
    // BufferedInputStream
    // BufferedOutputStream
    public static void copyFile(File srcFile, File destFile) throws IOException {
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
