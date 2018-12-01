package code_00_thread.threadSynchronization.juc_others;

import java.util.Random;

/**
 * Created by 18351 on 2018/12/1.
 */
public class ProducerConsumer {
    public static class Producer extends Thread{
        private Baozi baozi;

        public Producer(Baozi baozi) {
            this.baozi = baozi;
        }

        @Override
        public void run() {
            baozi.produce("狗不理","肉");
        }
    }

    public static class Consumer extends Thread{
        private Baozi baozi;

        public Consumer(Baozi baozi) {
            this.baozi = baozi;
        }

        @Override
        public void run() {
            baozi.consume();
        }
    }

    public static void main(String[] args) {
        Baozi baozi=new Baozi();
        Consumer consumer = new Consumer(baozi);
        consumer.start();
        Producer producer = new Producer(baozi);
        producer.start();
    }
}
