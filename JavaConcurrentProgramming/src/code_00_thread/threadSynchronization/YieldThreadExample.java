package code_00_thread.threadSynchronization;

/**
 * Created by 18351 on 2018/12/1.
 */
public class YieldThreadExample {
    private static class YieldThread extends Thread{
        @Override
        public void run() {
            for(int i=0;i<20;i++){
                System.out.println(this.getName()+"\t"+i);
                Thread.yield(); //暂停当前正在执行的线程对象，并执行其他线程。
            }
        }
    }

    public static void main(String[] args) {
        YieldThread yt1=new YieldThread();
        YieldThread yt2=new YieldThread();

        yt1.setName("邓超");
        yt2.setName("孙俪");

        yt1.start();
        yt2.start();
    }
}
