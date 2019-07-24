package code_01_activity.code_11_nullObject;

/**
 * 使用什么都不做的空对象来代替 NULL。
 *
 * 一个方法返回 NULL，意味着方法的调用端需要去检查返回值是否是 NULL，
 * 这么做会导致非常多的冗余的检查代码。
 * 并且如果某一个调用端忘记了做这个检查返回值，而直接使用返回的对象，
 * 那么就有可能抛出空指针异常。
 */
public class NullOperation extends AbstractOperation{
    @Override
    public void request() {
        //do nothing
    }
}
