package com.southeast.Semaphore;

/**
 * 跑道类
 * 实际上就是封装了跑道的编号
 */
public class Track {
    private int num;

    public Track(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return num + "号跑道上跑步";
    }
}
