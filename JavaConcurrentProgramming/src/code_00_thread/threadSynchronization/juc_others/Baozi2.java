package code_00_thread.threadSynchronization.juc_others;

/**
 * Created by 18351 on 2018/12/1.
 */
public class Baozi2 {
    private String name;
    private String pie;


    public void produce(String name,String pie){
        //生产数据
        this.name=name;
        this.pie=pie;
        System.out.println("做包子");
    }

    public void consume(){
        //消费数据
        System.out.println("吃"+this.name+"包子，馅是"+this.pie);
    }
}