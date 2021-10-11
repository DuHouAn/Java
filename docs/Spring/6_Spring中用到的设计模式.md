# Spring 中涉及到的设计模式

## 工厂模式

Spring 使用工厂模式可以通过 BeanFactory 或  ApplicationContext 创建 Bean。

```java
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
 
public class App {
    public static void main(String[] args) {
        ApplicationContext context = new FileSystemXmlApplicationContext(
                "xxxx");
 
        context.getBean("xxx"); //  使用 ApplicationContext 创建 Bean
    }
}
```

## 单例模式

Spring 通过 ConcurrentHashMap 实现单例注册表的特殊方式实现单例模式：

```java
// 通过 ConcurrentHashMap（线程安全） 实现单例注册表
private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);

public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
        Assert.notNull(beanName, "'beanName' must not be null");
        synchronized (this.singletonObjects) {
            // 检查缓存中是否存在实例  
            Object singletonObject = this.singletonObjects.get(beanName);
            if (singletonObject == null) {
                //...
                try {
                    singletonObject = singletonFactory.getObject();
                }
                //... 
                // 如果实例对象不不存在，那么注册到单例注册表中。
                addSingleton(beanName, singletonObject);
            }
            return (singletonObject != NULL_OBJECT ? singletonObject : null);
        }
    }
    // 将对象添加到单例注册表
    protected void addSingleton(String beanName, Object singletonObject) {
            synchronized (this.singletonObjects) {
                this.singletonObjects.put(beanName, (singletonObject != null ? singletonObject : NULL_OBJECT));

            }
        }
}
```

Spring 实现单例的方式：

- XML 配置方式：

  ```xml
  <bean id="testService" class="xxxx.TestServiceImpl" scope="singleton"/>
  ```

- 注解方式：

  ```java
  @Service
  @Scope("singleton")
  public class TestServiceImpl{
  }
  ```

## 原型模式

Spring 实现原型方式：

Spring 实现单例的方式：

- XML 配置方式：

  ```xml
  <bean id="testService" class="xxxx.TestServiceImpl" scope="prototype"/>
  ```

- 注解方式：

  ```java
  @Service
  @Scope("prototype")
  public class TestServiceImpl{
  }
  ```

## 代理模式

Spring AOP、Spring 事务管理都大量运用了代理模式。

## 适配器模式

Spring AOP 的增强或通知 (Advice) 使用到了适配器模式，与之相关的接口是 AdvisorAdapter  。Advice 常用的类型有：BeforeAdvice（前置通知）、AfterAdvice（后置通知）等，每个类型 Advice 都有对应的拦截器：MethodBeforeAdviceInterceptor、AfterReturningAdviceAdapter。Spring 预定义的 Advice 要通过对应的适配器，适配成 MethodInterceptor 接口(方法拦截器)类型的对象，例如 MethodBeforeAdviceInterceptor 负责适配 MethodBeforeAdvice。

Spring MVC 中的 DispatcherServlet 根据请求信息调用 HandlerMapping，解析请求对应的 Handler。解析到对应的 Handler 后，开始由 HandlerAdapter 处理。HandlerAdapter 作为期望接口，具体的适配器实现类用于对目标类进行适配，Controller 作为需要适配的类。
