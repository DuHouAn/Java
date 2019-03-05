package code_02_strcuture.code_06_proxy.java_static;

public class Client {
    public static void main(String[] args) {
        UserDao userDao=new UserDao();
        userDao.save();

        System.out.println("。。。使用代理后。。。");
        UserDaoProxy proxy=new UserDaoProxy(userDao);
        proxy.save();
    }
}
