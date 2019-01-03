package code_02_strcuture.code_06_proxy.java_cglib;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib代理工厂，
 * 实现了MthodInterceptor
 */
public class CglibProxyFactory implements MethodInterceptor{
    private Object target;

    public CglibProxyFactory(Object target){
        this.target=target;
    }

    public Object getInstance(){
        //1.工具类
        Enhancer enchancer=new Enhancer();
        //2.设置父类
        enchancer.setSuperclass(target.getClass());
        //3.设置回调函数
        enchancer.setCallback(this);
        //4.创建子类（代理对象）
        return enchancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object returnObj=null;
        if("save".equals(method.getName())){
            System.out.println("开启事务");
            returnObj=method.invoke(target,objects);
            System.out.println("关闭事务");
        }else{
            returnObj=method.invoke(target,objects);
        }
        return returnObj;
    }
}
