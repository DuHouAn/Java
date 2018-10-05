package com.southeast.CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 司令要求10个士兵去完成任务,先集合10个然后去一起完成任务,等全部完成后 司令才会宣布任务完成!
 */
public class CyclicBarrierExample3 {
    static class Soldier implements Runnable {
        private String soldier;
        private final CyclicBarrier cyclicBarrier;

        public Soldier(String soldier, CyclicBarrier cyclicBarrier) {
            this.soldier = soldier;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await(); //等待所有士兵到齐（实际上就是让所有线程都等待）
                doWork();
                cyclicBarrier.await(); //等待所有士兵完成任务 （实际上还是让所有线程都等待）
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        public void doWork(){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(soldier+"："+"任务完成");
        }
    }

    static class BarrierRun implements Runnable { //线程类
        int flag;
        int N;

        public BarrierRun(int flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        @Override
        public void run() {
            if (flag==1) { //1表示的是任务完成
                System.out.println("司令:[士兵" + N + "个,集合完毕!]");
                //士兵集合完毕后，flag状态就要发生相应的变化
                flag=2;
            } else {
                System.out.println("司令:[士兵" + N + "个,任务完成!]");
            }
        }
    }

    public static void main(String[] args) {
        final int N = 5;
        Thread[] allSoldier = new Thread[N];

        int flag = 1;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new BarrierRun(flag, N));// BarrierRun是线程类

        System.out.println("集合队伍! ");
        for (int i = 0; i < N; i++) {
            System.out.println("士兵" + i + "报道! ");
            //10个随机的线程
            allSoldier[i] = new Thread(new Soldier("士兵" + i,cyclicBarrier ));
            //所有的士兵都是同一个cyclicBarrier
            allSoldier[i].start();
        }
    }
}


