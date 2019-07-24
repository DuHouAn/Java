package code_02_strcuture.code_03_decorator.phone;

/**
 * Decorator：所有装饰器的抽象父类，需要定义一个与组件接口一致的接口，并持有一个Component对象，其实就是持有一个被装饰的对象。
 * 注意，这个被装饰的对象不一定是最原始的那个对象了，
 * 也可能是被其它装饰器装饰过后的对象，反正都是实现的同一个接口，也就是同一类型。
 */
public abstract class PhoneDecorator implements Phone{
    protected Phone phone;

    public PhoneDecorator(Phone phone){
        this.phone=phone;
    }

    @Override
    public void call() {
        phone.call();
    }
}
