package com.southeast.extension.timer;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时删除  使用定时器 Timer和 TimerTask
 * 指定目录的文件夹
 */
public class DeleteFolderTimer {
    static class MyTask extends TimerTask{
        private Timer t;

        public MyTask(Timer t) {
            this.t = t;
        }

        @Override
        public void run() {
            File file=new File("demo");
            deleteFolder(file);
            t.cancel();
        }
    }

    public static void deleteFolder(File folder){
        if(folder!=null){
            File[] files=folder.listFiles();
            for(File file:files){
                if(file.isDirectory()){
                    deleteFolder(file);
                }else{
                    System.out.println(file.getName()+":"+file.delete());
                }
            }
            System.out.println(folder.getName()+":"+folder.delete());
        }
    }

    public static void main(String[] args) {
        Timer timer=new Timer();
        timer.schedule(new MyTask(timer),3000);
    }
}
