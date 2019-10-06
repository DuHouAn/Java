# Spring中涉及到的设计模式

## 工厂模式

- [工厂模式](OO/2_创建型.md#2-简单工厂simple-factory)

BeanFactory 用来创建各种不同的 Bean。

## 单例模式

在 Spring 配置文件中定义的 Bean 默认为单利模式。

## 模板方法模式

用来解决代码重复的问题。比如 JdbcTempolate。

## 代理模式

AOP、事务都大量运用了代理模式。

## 原型模式

特点在于通过"复制"一个已经存在的实例来返回新的实例，而不是新建实例。被复制的实例就是我们所称的"原型"，这个原型是可定制的。

## 责任链模式

在 SpringMVC 中，会经常使用一些拦截器(HandlerInterceptor)，当存在多个拦截器的时候，所有的拦截器就构成了一条拦截器链。

## 观察者模式

Spring 中提供了一种监听机制，即 ApplicationListenber，可以实现 Spring 容器内的事件监听。