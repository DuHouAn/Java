package com.southeast.Semaphore;

import java.util.concurrent.*;

/**
 * Semaphore就是操作系统中的信号量，可以控制对互斥资源的访问线程数。
 * 下面举个例子，比如说操场上有5个跑道，一个跑道一次只能有一个学生在上面跑步，
 * 一旦所有跑道在使用，那么后面的学生就需要等待，直到有一个学生不跑了
 */
public class SemaphoreExample2 {
    static class Student implements Runnable {

        private int num;
        private PlayGround playground;

        public Student(int num, PlayGround playground) {
            this.num = num;
            this.playground = playground;
        }

        @Override
        public void run() {
            try {
                //获取跑道
                Track track = playground.getTrack(); //这里占用跑道
                if (track != null) {
                    System.out.println("学生" + num + "在" + track.toString() + "上跑步");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("学生" + num + "从" + track.toString()+"上下来了");
                    //释放跑道
                    playground.releaseTrack(track); //这里释放原来占用的跑道
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Executor executor = Executors.newCachedThreadPool();
        PlayGround playground = new PlayGround();
        for (int i = 0; i < 20; i++) {
            executor.execute(new Student(i+1,playground));
        }
        ((ExecutorService) executor).shutdown();
    }
}
