# Spring中Bean的生命周期

## Bean 的创建过程

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/spring_1.png" width="200px"/></div>



## Bean 的销毁过程

- 若实现了 DisposableBean 接口，则会调用 destroy 方法
- 若配置了 destroy-method 属性，则会调用其配置的销毁方法



## 详细过程

<div align="center"><img src="https://gitee.com/duhouan/ImagePro/raw/master/java-notes/spring/spring_2.png" width="600px"/></div>



1、Spring 对 Bean 进行实例化。

2、Spring 将值和 Bean 的引用注入到 Bean 对应的属性中。

3、如果 Bean 实现了 BeanNameAware 接口，Spring 将 bean 的 id 传递给 setBeanName() 接口方法。

4、如果 Bean 实现了 BeanFactoryAware 接口，Spring 将调用 setBeanFactory() 接口方法，将 BeanFactory 容器实例传入。

5、如果 Bean 实现了 ApplicationContextAware 接口，Spring 将调用 setApplicationContext() 接口方法，将应用上下文的引用传入。

6、如果 Bean 实现了BeanPostProcessor 接口，Spring 将调用 postProcessBeforeInitialization() 接口方法。

7、如果 Bean 实现了InitializingBean 接口，Spring 将调用他们的 afterPropertiesSet() 接口方法

8、如果Bean 实现了 init-method 声明了初始化方法，该方法也会被调用。

9、如果 Bean 实现了BeanPostProcessor 接口，Spring 将调用 postProcessAfterInitialization() 接口方法。

10、此时 Bean 已经准备就绪，可以被应用程序使用了，他们将一一直驻留在应用上下文中，一直到该应用上下文被销毁。

11、如果 Bean 实现了 DisposableBean 接口，Spring 将调用它的 destroy() 接口方法。

12、如果 Bean 使用 destroy-method 声明了销毁方法，方法也会被调用。