package code_02_strcuture.code_06_proxy.java_dynamic;

public class Client {
    public static void main(String[] args) {
        IUserDao userDao=new UserDao();
        userDao.save();
        userDao.update();

        System.out.println("。。。使用代理后。。。");
        PrxoyFactory factory=new PrxoyFactory(userDao);
        //Java的动态代理目前只能代理接口
        IUserDao proxy=(IUserDao)factory.getInstance();
        proxy.save();
        proxy.update();
    }
}
