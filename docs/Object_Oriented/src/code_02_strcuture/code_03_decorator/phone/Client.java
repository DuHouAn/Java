package code_02_strcuture.code_03_decorator.phone;

/**
 * Created by 18351 on 2019/1/2.
 */
public class Client {
    public static void main(String[] args) {
        //普通手机
        Phone p=new IPhone();
        p.call();
        System.out.println("====================");

        //使用装饰类 -->增加了彩铃功能
        RingPhoneDecorator rpd=new RingPhoneDecorator(p);
        rpd.call();
        System.out.println("====================");

        //使用装饰类 -->增加了听音乐的功能
        MusicPhoneDecorator mpd=new MusicPhoneDecorator(p);
        mpd.call();
        System.out.println("====================");

        //装饰类可以组合 -->先响铃-->接电话-->接完电话后，就听音乐
        //装饰类的特点
        MusicPhoneDecorator mpd2=new
                MusicPhoneDecorator(new RingPhoneDecorator(p));
        mpd2.call();
        System.out.println("===========================");

        RingPhoneDecorator mpd3= new RingPhoneDecorator(new MusicPhoneDecorator(p));
        mpd3.call();
        System.out.println("===========================");
    }
}
