package code_00_thread.threadSynchronization.juc_others;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by 18351 on 2018/12/1.
 */
public class ProducerConsumer2 {
    private static BlockingQueue<Baozi2> queue = new ArrayBlockingQueue<>(5);

    public static class Producer extends Thread {
        private Baozi2 baozi;

        public Producer(Baozi2 baozi) {
            this.baozi = baozi;
        }

        @Override
        public void run() {
            queue.add(baozi);
            baozi.produce("狗不理","肉");
        }
    }

    public static class Consumer extends Thread{
        @Override
        public void run() {
            Baozi2 baozi= null;
            try {
                baozi = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            baozi.consume();
        }
    }

    public static void main(String[] args) {
        Baozi2 baozi=new Baozi2();

        for (int i = 0; i < 2; i++) {
            Producer producer = new Producer(baozi);
            producer.start();
        }
        for (int i = 0; i < 5; i++) {
            Consumer consumer = new Consumer();
            consumer.start();
        }
        for (int i = 0; i < 3; i++) {
            Producer producer = new Producer(baozi);
            producer.start();
        }
    }
}
