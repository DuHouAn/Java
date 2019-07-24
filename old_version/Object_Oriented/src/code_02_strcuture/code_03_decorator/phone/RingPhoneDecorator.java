package code_02_strcuture.code_03_decorator.phone;

/**
 * 具体的装饰类，实现具体要向被装饰对象添加的功能。
 */
public class RingPhoneDecorator extends PhoneDecorator {
    public RingPhoneDecorator(Phone phone) {
        super(phone);
    }

    @Override
    public void call(){
        System.out.println("接电话前响铃");
        phone.call();
    }
}
