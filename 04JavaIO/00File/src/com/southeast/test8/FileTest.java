package com.southeast.test8;

import java.io.File;

/**
 * 判断liuyi目录下是否有后缀名为.txt的文件，如果有，就输出此文件名称
 *
 * 分析：
 * 		A:封装判断目录liuyi 为文件对象
 * 		B:获取该目录下所有文件或者文件夹的File数组
 * 		C:遍历该File数组，得到每一个File对象，然后判断
 * 		D:是否是文件
 * 			是：继续判断是否以.txt结尾
 * 				是：就输出该文件名称
 * 				否：不搭理它
 * 			否：不搭理它
 */
public class FileTest {
    public static void main(String[] args) {
        File file=new File("liuyi");

        File[] fileList=file.listFiles();
        if(fileList!=null){
            for(File f:fileList){
                if(!f.isDirectory()){
                    String fileName=f.getName();
                    if(fileName.endsWith(".txt")){
                        System.out.println(fileName);
                    }
                }
            }
        }
    }
}
