package com.southeast.Semaphore;

import java.util.concurrent.Semaphore;

public class PlayGround {
    //操场上有5个跑道，
    private Track[] tracks={
            new Track(1),
            new Track(2),
            new Track(3),
            new Track(4),
            new Track(5)};
    private volatile boolean[] used = new boolean[5]; //用来判断各个跑道是否在使用,默认全是false，表示没有在使用

    private Semaphore semaphore = new Semaphore(5, true);
    //fair值为true,公平模式就是调用acquire的顺序就是获取许可证的顺序，遵循FIFO；
    //Semaphore就是操作系统中的信号量，可以控制对互斥资源的访问线程数。这里就对5个跑道进行了控制

    //获取一个跑道
    public Track getTrack() throws InterruptedException {
        semaphore.acquire(1); //每个acquire方法阻塞，直到有一个许可证可以获得然后拿走一个许可证
        return getNextAvailableTrack();
    }

    // //每个release方法增加一个许可证，这可能会释放一个阻塞的acquire方法
    //空出一个跑道
    public void releaseTrack(Track track) {
        if (makeAsUsed(track)) //判断当前跑道是否为空，如果是不为空，则空出来
            semaphore.release(1);
    }

    // 遍历，找到一个没人用的跑道
    private Track getNextAvailableTrack(){
        for(int i=0;i<tracks.length;i++){
            if(!used[i]){
                used[i]=true;
               return tracks[i];
            }
        }
        return null;
    }

    //判断当前跑道是否为一个空跑道
    private boolean makeAsUsed(Track track) {
        for (int i = 0; i < used.length; i++) {
            if (tracks[i] == track) {
                if (used[i]) {
                    used[i] = false;
                    return true;
                } else {
                    return false;
                }

            }
        }
        return false;
    }
}
