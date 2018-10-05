package com.southeast.NIOTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *  复制文件多级文件夹
 */
public class CopyFile3 {
    public static void main(String[] args) throws IOException {
        File srcFile=new File("nio\\aaa");
        File destFile=new File("niocopy");
        if(!destFile.exists()){
            destFile.mkdir();
        }
        copyFolder(srcFile,destFile);
    }

    public static void copyFolder(File srcFile,File destFile) throws IOException{
        if(srcFile.isDirectory()){
            //创建destFile下的文件夹
            File newFolder=new File(destFile,srcFile.getName());
            newFolder.mkdir();//创建该文件夹-->名称与srcFile名称一致

            //遍历srcFile下面的文件
            File[] files=srcFile.listFiles();
            if(files!=null){
                for(File file:files){
                    copyFolder(file,newFolder); //将文件夹复制到新创建的文件夹中
                }
            }
        }else{ //srcFile是文件，则直接复制到目标文件夹下面
            File newFile=new File(destFile,srcFile.getName());
            copyFile(srcFile,newFile);
        }
    }

    public static void copyFile(File srcFile,File destFile) throws IOException { // 265 ms
        FileInputStream fis=new FileInputStream(srcFile);
        FileOutputStream fos=new FileOutputStream(destFile);

        FileChannel fcin=fis.getChannel();
        FileChannel fcout=fos.getChannel();

        ByteBuffer byteBuffer=ByteBuffer.allocateDirect(1024);
        int by=0;
        while((by=fcin.read(byteBuffer))!=-1){
            //TODO:切换读写
            byteBuffer.flip();//缓存区由写切换到读
            fcout.write(byteBuffer);
            byteBuffer.clear();//清空缓存区
        }

        fis.close();
        fos.close();
    }
}
