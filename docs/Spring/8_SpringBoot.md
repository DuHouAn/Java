# SpringBoot

## 简介

SpringBoot 是由 pivotal 团队开发的全新的Spring 框架，其设计初衷是简化 Spring 应用复杂的搭建和开发过程。该框架提供了一套简单的 Spring 模块依赖和管理工具，从而避免了开发人员处理复杂的模块依赖和版本冲突问题，同事提供打包即可用 Web 服务。

SpringBoot 特点如下：

- 快速创建独立的 Spring 应用程序
- 嵌入 Tomcat Web 容器，可快速部署。
- 自动配置 JAR 包依赖和版本控制，简化 Maven 配置
- 自动装配 Spring 实例，不需要 XML 配置
- 提供诸如性能指标、健康检查、外部配置等线上监控和配置功能。

## 常用注解

### @SpringBootApplication

Spring Boot 项目的基石，创建 SpringBoot 项目之后会默认在主类加上。

@SpringBootApplication 可以认为是 @Configuration 、@EnableAutoConfiguration 和 @ComponentScan 注解的集合。其中

- @EnableAutoConfiguration：启用 SpringBoot 的自动配置机制
- @ComponentScan： 扫描被 @Component / @Service / @Controller 注解的 Bean，注解默认会扫描该类所在的包下所有的类。
- @Configuration：允许在 Spring 上下文中注册额外的 Bean 或导入其他配置类

### @Value &  @ ConfigurationProperties & @PropertySource

通过 @Value("${property}") 读取比较简单的配置信息；

通过 @ConfigurationProperties 读取配置信息并与 bean 绑定；

通过 @PropertySource 读取指定 properties 文件。

### @ControllerAdvice & @ExceptionHandler

@ControllerAdvice：注解定义全局异常处理类

@ExceptionHandler：注解声明异常处理方法

## 实战

- [SpringBoot 官网](https://spring.io/projects/spring-boot/)

- [SpringBoot 实战](https://snailclimb.gitee.io/springboot-guide/#/)

# SpringBoot 面试题

- [SpringBoot 自动装配原理](https://www.cnblogs.com/javaguide/p/springboot-auto-config.html)

- [Spring Boot面试题（2020最新版）](https://thinkwon.blog.csdn.net/article/details/104397299?utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-2.no_search_link&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-2.no_search_link)
