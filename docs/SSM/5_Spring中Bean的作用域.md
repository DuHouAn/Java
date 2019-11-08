# Spring 中 Bean 的作用域

Spring Framework 支持五种作用域：

|     类别      |                             说明                             |
| :-----------: | :----------------------------------------------------------: |
|   singleton   | 在 SpringIoC 容器中仅存在一个 Bean 实例，Bean 以单例方式存在 |
|   prototype   |         每次从容器中调用 Bean 时，都返回一个新的实例         |
|    request    | 每次HTTP请求都会创建一个新的 Bean,该作用域仅适用于 WebApplicationContext 环境 |
|    session    | 同一个 Http Session 共享一个 Bean,不同 Session 使用不同 Bean,仅适用于WebApplicationContext 环境 |
| globalSession | 一般同于 Portlet 应用环境，该作用域仅适用于 WebApplicationContext 环境 |

注意：五种作用域中，request、session 和 global session 三种作用域仅在基于 web 的应用中使用(不必关心你所采用的是什么web应用框架)，只能用在基于 web 的 Spring ApplicationContext 环境。

## 1. singleton

当一个 Bean 的作用域为 singleton，那么Spring IoC容器中只会存在一个**共享的 Bean 实例**， 并且所有对 Bean 的请求，只要 id 与该 Bean 定义相匹配，则只会**返回 Bean 的同一实例**。

singleton 是单例类型(对应于单例模式)，就是**在创建容器时就同时自动创建一个Bean对象**， 不管你是否使用，但我们可以指定Bean节点的 lazy-init="true" 来延迟初始化Bean， 这时候，只有在第一次获取Bean时才会初始化Bean，即第一次请求该bean时才初始化。 每次获取到的对象都是同一个对象。 注意，singleton 作用域是Spring中的**缺省作用域**。

- 配置文件XML中将 Bean 定义成 singleton ：

  ```html
  <bean id="ServiceImpl" class="com.southeast.service.ServiceImpl" scope="singleton">
  ```

- @Scope 注解的方式：

```java
@Service
@Scope("singleton")
public class ServiceImpl{

}
```



## 2. prototype

当一个Bean的作用域为 prototype，表示一个 Bean 定义对应多个对象实例。 prototype 作用域的 Bean 会导致在每次对该 Bean 请求 (将其注入到另一个 Bean 中，或者以程序的方式调用容器的 getBean() 方法)时都会创建一个新的 bean 实例。 prototype 是原型类型，它在我们创建容器的时候并没有实例化， 而是当我们获取Bean的时候才会去创建一个对象，而且我们每次获取到的对象都不是同一个对象。

根据经验，**对有状态的 Bean 应该使用 prototype 作用域，而对无状态的 Bean 则应该使用 singleton 作用域。**

- 配置文件XML中将 Bean 定义成 prototype ：

```html
<bean id="ServiceImpl" class="com.southeast.service.ServiceImpl" scope="prototype">
```

或者

```html
<bean id="ServiceImpl" class="com.southeast.service.ServiceImpl" singleton="false"/>
```

- @Scope 注解的方式：

```java
@Service
@Scope("prototype")
public class ServiceImpl{

}
```



> **Spring  中线程安全问题**

有状态 Bean 和 无状态 Bean：

- **有状态 Bean**

  对象中有实例变量（成员变量），可保存数据；

  非线程安全。

- **无状态 Bean**

  对象中午实例变量，不能保存数据，可在多线程环境下共享；

  线程安全。

Spring 中有状态 Bean 如何保证线程安全？

2 种方式保证线程安全：

- 采用 ThreadLocal 进行处理
- 采用原型模式，每次有 Bean 请求时，都会创建一个新的 Bean 实例



## 3. request

request只适用于**Web程序**，每一次 HTTP 请求都会产生一个新的 Bean ， 同时该 Bean 仅在当前HTTP request内有效，当请求结束后，该对象的生命周期即告结束。

在 XML 中将 bean 定义成 request ，可以这样配置：

- 配置文件XML中将 Bean 定义成 prototype ：

```html
<bean id="ServiceImpl" class="com.southeast.service.ServiceImpl" scope="request">
```



## 4. session

session只适用于**Web程序**， session 作用域表示该针对每一次 HTTP 请求都会产生一个新的 Bean， 同时**该 Bean 仅在当前 HTTP session 内有效**。 与request作用域一样，可以根据需要放心的更改所创建实例的内部状态， 而别的 HTTP session 中根据 userPreferences 创建的实例， 将不会看到这些特定于某个 HTTP session 的状态变化。 当HTTP session最终被废弃的时候，在该HTTP session作用域内的bean也会被废弃掉。

```html
<bean id="userPreferences" class="com.foo.UserPreferences" scope="session"/>
```



## 5. globalSession

globalSession 作用域**类似于标准的 HTTP session** 作用域， 不过仅仅在基于 portlet 的 Web 应用中才有意义。 Portlet 规范定义了全局 Session 的概念， 它被所有构成某个 portlet web 应用的各种不同的 portlet所共享。 在globalSession 作用域中定义的 bean 被限定于全局portlet Session的生命周期范围内。

```html
<bean id="user" class="com.foo.Preferences "scope="globalSession"/>
```
