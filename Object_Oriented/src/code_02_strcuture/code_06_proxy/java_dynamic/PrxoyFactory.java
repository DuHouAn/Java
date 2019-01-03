package code_02_strcuture.code_06_proxy.java_dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 18351 on 2019/1/3.
 */
public class PrxoyFactory {
    private Object target;

    public PrxoyFactory(Object target){
        this.target=target;
    }

    public Object getInstance(){
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    //proxy - 在其上调用方法的代理实例
                    //method - 对应于在代理实例上调用的接口方法的 Method 实例。
                    //args - 包含传入代理实例上方法调用的参数值的对象数组，

                    //如果接口方法不使用参数，则为 null。
                    //基本类型的参数被包装在适当基本包装器类（如 java.lang.Integer 或 java.lang.Boolean）的实例中。
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object obj=null;
                        String methodName=method.getName();
                        //只对save方法增强
                        if("save".equals(methodName)){
                            System.out.println("提交事务");
                            //target 就是目标对象，arge就是方法的参数
                            obj=method.invoke(target,args);
                            System.out.println("结束事务");
                        }else{
                            obj=method.invoke(target,args);
                        }
                        return obj;
                    }
                }
        );
    }
}
