package code_02_strcuture.code_03_decorator.phone;

/**
 * 具体的装饰类，实现具体要向被装饰对象添加的功能。
 */
public class MusicPhoneDecorator extends PhoneDecorator{
    public MusicPhoneDecorator(Phone phone) {
        super(phone);
    }

    @Override
    public void call(){
        phone.call();
        System.out.println("接完电话听点音乐");
    }
}
