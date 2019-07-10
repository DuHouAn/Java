package code_02_strcuture.code_06_proxy.java_static;

/**
 * RealSubject
 */
public class UserDao implements IUserDao{
    @Override
    public void save() {
        System.out.println("保存数据");
    }
}
