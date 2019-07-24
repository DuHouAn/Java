package code_02_character;

import java.io.*;

/**
 * Created by 18351 on 2019/1/5.
 */
public class CopyFolders {
    public static void main(String[] args) throws IOException {
        File srcFile=new File("demo2");
        File destFile=new File("demo3");
        copyFolder(srcFile,destFile);
    }

    //复制多级文件夹
    public static void copyFolder(File srcFile,File destFile) throws IOException {
        if(srcFile.isDirectory()){
            //在destFile下创建新文件夹
            File newDirectory=new File(destFile,srcFile.getName());
            newDirectory.mkdir();

            // 获取该File对象下的所有文件或者文件夹
            File[] files=srcFile.listFiles();
            if(files!=null){
                //将srcFile下的所有文件都复制到新destFile
                for(File file:files){
                    copyFolder(file,newDirectory);
                }
            }
        }else{
            File newFile=new File(destFile,srcFile.getName());
            copyFile(srcFile,newFile);
        }
    }

    //复制文件
    public static void copyFile(File srcFile,File destFile) throws IOException {
        BufferedInputStream bis=new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(destFile));

        int len=0;
        byte[] bys=new byte[1024];
        while((len=bis.read(bys))!=-1){
            bos.write(bys,0,len);
        }

        bos.close();
        bis.close();
    }
}