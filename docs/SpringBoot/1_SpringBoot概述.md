# Spring Boot 概述

## Spring Boot 特点

SpringBoot 是由 pivotal 团队开发的全新的Spring 框架，其设计初衷是**简化 Spring 应用复杂的搭建和开发过程**。该框架提供了一套简单的 Spring 模块依赖和管理工具，从而避免了开发人员处理复杂的模块依赖和版本冲突问题，同事提供打包即可用 Web 服务。

SpringBoot 特点如下：

- 快速创建独立的 Spring 应用程序
- 嵌入 Tomcat Web 容器，可快速部署
- 自动配置 JAR 包依赖和版本控制，简化 Maven 配置
- 自动装配 Spring 实例，不需要 XML 配置
- 提供诸如性能指标、健康检查、外部配置等线上监控和配置功能。

## Spring Boot Application Starters

Starters 是一组**资源依赖描述**，用于为不同的 SpringBoot 应用提供一站式服务，而不必像传统的 Spring 项目那样，需要开发人员处理服务和服务之间的复杂依赖关系。例如，如果需要开发 REST 服务或 Web 应用程序时，只需只需添加一个 **spring-boot-starter-web** 依赖就可以了，具体的依赖细节由 Starters 统一处理，不需要应用程序分别处理各个 Jar 包之间的依赖关系。

```html
<!-- 引入 spring-boot-starter-web 依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## Spring Boot 支持的内嵌 Servlet 容器

Spring Boot 支持的内嵌 Servlet 容器有：

- Tomcat 9.0
- Jetty 9.4
- Undertow 2.0

Spring Boot ( spring-boot-starter-web) 使用 Tomcat 作为默认的嵌入式 Servlet 容器，如果需要使用 Jetty 的话需要修改 pom.xml (Maven) 或者 build.gradle (Gradle) 文件配置。

```html
<!-- 修改 pom.xml 文件配置 -->
<!--从Web启动器依赖中排除Tomcat-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>

<!--添加Jetty依赖-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

```html
// 修改 build.gradle 文件配置（看上去更加简洁）
compile("org.springframework.boot:spring-boot-starter-web") {
     exclude group: 'org.springframework.boot', module: 'spring-boot-starter-tomcat'
}
compile("org.springframework.boot:spring-boot-starter-jetty")
```

