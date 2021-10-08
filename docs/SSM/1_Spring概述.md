# Spring 概述

## Spring 的特性

Spring 基于 J2EE 技术实现了一套**轻量级**的 Java Web Service 系统应用框架，旨在提高开发人员的开发效率以及系统的可维护性。Spring 的特性包括：

- 轻量级

  从 Jar 包的大小上来说，核心 Jar 包 `spring-web-5.2.0.RELEASE.jar`和 `spring-core-5.2.0.RELEASE.jar`均为 1.4 M 左右；

  从系统的资源使用上来说，Spring 运行期间只需要少量的操作系统资源（内存和 CPU）便能稳定运行。

- 面向容器

  Spring 实现了对象的配置化生成和对象的生命周期管理，所以是面向容器的。

- 控制反转

- 面向切面

- 框架灵活

## Spring 的模块

Spring 为企业应用程序提供一站式服务。Spring 模块提供的常用模块有核心容器层（Core Container）、数据访问层（Data Access）、Web 应用层（Web Access）。

### 核心容器层

核心容器层包括 Spring-Beans、Spring-Core、Spring-Context  等模块。

- Spring-Beans

  基于工厂模式实现对象的创建。Spring-Beans 通过 xml 配置文件实现了声明式的对象管理，将对象之间复杂的依赖关系从实际编码逻辑中解耦出来。

- Spring-Core

  Spring  的核心功能实现，提供 IoC 依赖注入功能的支持。

- Spring-Context

  在 Spring-Beans 和 Spring-Core 模块的基础上构建起来的。Spring-Context 模块继承自  Spring-Beans 模块，并且添加了国际化、事件传播、资源加载和透明地创建上下文等功能。

### 数据访问层

数据访问层包括：JDBC、ORM、OXM、JMS 和 TX 模块。

- JDBC (Java Data Base Connectivity)

  提供了对数据库访问的抽象 JDBC。不同的数据库都有自己独立的 API 用于操作数据库，而 Java 程序只需要和 JDBC API 交互，这样就屏蔽了数据库的影响。

- ORM (Object Relational Mapping)

  提供对 Hibernate 等 ORM 框架的支持。

- OXM (Object XML Mapping)

  提供对 Castor 等 OXM 框架的支持。

- JMS (Java Message Service)

  JMS 模块包括消息的生产和消费功能。从 Spring 4.1 开始，Spring 集成了 Spring-Messaging 模块，用于实现对消息队列的支持。

- TX

  提供对事务的支持。

### Web 应用层

Web 应用层主要包括 Web 交互和数据传输等相关功能，包括 Web、Web-MVC、Web-Socket 和 Web-Flux。

- Web

  提供了面向 Web 应用的基本功能。

- Web-MVC

  提供对 Spring MVC 的实现。

- Web-Socket

  提供了对 WebSocket 的支持，WebSocket 可以让客户端和服务端进行双向通信。

- Web-Flux

  提供对 WebFlux 的支持。

  目前最新的 5.x 版本中 Web 模块的 Portlet 组件已经被废弃掉，同时增加了用于异步响应式处理的 WebFlux 组件。

### 其他重要模块

- Spring AOP

  提供了面向切面的编程实现，允许应用程序通过定义方法拦截器和切入点来实现系统功能和业务功能之间的解耦。

- Spring Aspects

  提供了 Spring 与 AspectJ 的集成，是一个面向切面编程的模块。

## Spring 的注解

### @Contoller

SpringMVC 中，控制器 Controller 负责处理 DispatcherServlet 分发的请求，它把用户请求的数据经过业务处理层处理之后封装成一个 Model，然后再把该 Model 返回给对应的 View 进行展示。

SpringMVC 提供了一个非常简便的定义 Controller 的方法，无需继承特定的类或者接口，只需使用 @Controller 标记一个类是 Contoller。

### @RequestMapping

使用 @RequestMapping 来映射 URL 到 Controller，或者到 Controller 的处理方法上。method 的值一旦指定，则处理方法只对指定的 HTTP method 类型请求处理。

可以为多个方法映射相同的 URL 和不同的 HTTP method 类型，Spring MVC 根据请求的 method 类型是可以区分开这些方法的。

### @RequestParam & @PathVariable

在 SpringMVC 中，两者的作用都是将 request 里的参数值绑定到 Controller 里的方法参数中，区别在于 URL 的写法不同。

- 使用 @RequestParam 时，URL 是这样的：

```html
http://host:port/path?参数名=参数值
```

- 使用 @PathVariable 时，URL 是这样的：

```html
http://host:port/path/参数值
```

### @ResponseBody

该注解用于将 Controller 中方法返回的对象，通过适当的 HttpMessageConverter 转换为指定的格式后，写入到 Response 对象的 bodys 数据区。

### @Service & @Controller & @Repository & @Component

 @Service、 @Contrller、 @Repository 其实这 3 个注解和 @Component 是等效的，用在实现类上：

- @Service 用于标注业务层组件
- @Controller 用于标注控制层组件
- @Repository 用于编著数据访问组件
- @Component 泛指组件，当组件不好归类时，可以使用这个注解进行标注

### @Value

在 Spring 3.0 中，可以通过使用 @Value，对一些如 xxx.properties 文件中的文件，进行键值对的注入。

### @Autowired

@Autowired 可以对成员变量、成员方法和构造函数进行标注，来完成自动装配工作。

### @Autowired & @Resource

@Autowired 是 Spring 提供的注解，采用的策略是**按照类型**注入的：

```java
public class UserService{
    @Autowired
    userDao; // 在 Spring 容器中找到类型为 UserDao 的对象，将其注入
}
```

存在问题：同一类型有多个 Bean，可以使用 @Qualifier 具体去装配哪个对象。

```java
public class UserService{
    @Autowired
    @Qualifier(name="userDao")
    userDao; // 在 Spring 容器中找到类型为 UserDao，名称为 userDao 的对象，将其注入
}
```

 @Resource 是 J2EE 提供的注解，默认是**按照名称**注入的：

```java
Service{
    @Resource
    userDao; // 自动按名称进行装配
    
    @Resource(name="studentDao")
    studentDao; // 按名称进行装配，找不到会抛出异常
    
    @Resource(type="TeacherDao")
    teacherDao; // 按类型进行装配，找不到或者找到多个都会抛出异常
    
    @Resource(name="manDao",type="ManDao")
    manDao;// 找唯一匹配的 Bean 进行装配，如果找不到则会抛出异常
}
```
