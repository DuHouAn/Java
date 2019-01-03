package code_02_strcuture.code_06_proxy.java_static;

/**
 * Proxy
 * 1. 实现与具体的目标对象一样的接口，这样就可以使用代理来代替具体的目标对象
 * 2.保存一个指向具体目标对象的引用**，可以在需要的时候调用具体的目标对象，
 * 可以控制对具体目标对象的访问，并可能负责创建和删除它
 */
public class UserDaoProxy implements IUserDao{
    private UserDao target;

    public UserDaoProxy(UserDao userDao){
        this.target=userDao;
    }

    @Override
    public void save() {
        System.out.println("==保存之前增强==");
        target.save();
        System.out.println("==保存之后增强==");
    }
}
