# Spring中涉及到的设计模式

## 工厂模式

- [工厂模式](https://github.com/DuHouAn/Java/blob/master/docs/OO/2_%E5%88%9B%E5%BB%BA%E5%9E%8B.md#2-%E7%AE%80%E5%8D%95%E5%B7%A5%E5%8E%82simple-factory)

BeanFactory 用来创建各种不同的 Bean。

## 代理模式

- [代理模式](https://github.com/DuHouAn/Java/blob/master/docs/OO/4_%E7%BB%93%E6%9E%84%E5%9E%8B.md#7-%E4%BB%A3%E7%90%86proxy)

AOP、事务都大量运用了代理模式。

## 单例模式

- [单例模式](https://github.com/DuHouAn/Java/blob/master/docs/OO/2_%E5%88%9B%E5%BB%BA%E5%9E%8B.md#1-%E5%8D%95%E4%BE%8Bsingleton)

在 Spring 配置文件中定义的 Bean 默认为单利模式。

## 原型模式

- [原型模式](https://github.com/DuHouAn/Java/blob/master/docs/OO/2_%E5%88%9B%E5%BB%BA%E5%9E%8B.md#6-%E5%8E%9F%E5%9E%8B%E6%A8%A1%E5%BC%8Fprototype)

特点在于通过"复制"一个已经存在的实例来返回新的实例，而不是新建实例。被复制的实例就是我们所称的"原型"，这个原型是可定制的。

## 责任链模式

- [责任链模式](https://github.com/DuHouAn/Java/blob/master/docs/OO/3_%E8%A1%8C%E4%B8%BA%E5%9E%8B.md#1-%E8%B4%A3%E4%BB%BB%E9%93%BEchain-of-responsibility)

在 SpringMVC 中，会经常使用一些拦截器(HandlerInterceptor)，当存在多个拦截器的时候，所有的拦截器就构成了一条拦截器链。

## 观察者模式

- [观察者模式](https://github.com/DuHouAn/Java/blob/master/docs/OO/3_%E8%A1%8C%E4%B8%BA%E5%9E%8B.md#7-%E8%A7%82%E5%AF%9F%E8%80%85observer)

Spring 中提供了一种监听机制，即 ApplicationListenber，可以实现 Spring 容器内的事件监听。