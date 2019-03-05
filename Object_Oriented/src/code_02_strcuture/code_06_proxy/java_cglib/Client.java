package code_02_strcuture.code_06_proxy.java_cglib;

public class Client {
    public static void main(String[] args) {
        IUserDao userDao=new UserDao();
        userDao.save();
        userDao.update();

        System.out.println("。。。使用代理后。。。");
        CglibProxyFactory factory=new CglibProxyFactory(userDao);
        //Cglib即可以代理接口，也可以代理对象
        IUserDao proxy=(IUserDao)factory.getInstance();
        //UserDao proxy=(UserDao)factory.getInstance();
        proxy.save();
        proxy.update();
    }
}