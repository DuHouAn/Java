# Spring MVC 原理

## MVC (Model-View-Controller)

MVC 即 Model-View-Controller，是一种复合模式。

- Model：模型。封装数据源和所有基于这些数据的操作
- View：视图。用来显现模型
- Controller：控制器。封装外界作用于模型的操作

模型利用 “观察者模式” 让控制器和视图随最新的状态改变而更新；

视图和控制器实现 “策略模式”，控制器是视图的行为，若希望有不同的行为，可直接换一个控制器；

视图内部则是利用 “组合模式”。

MVC 具有以下优点：

- 三个模块可共享一个模型，大大提高代码的可重用性
- 三个模块相互独立，耦合性较低
- Controller 提高了应用程序的灵活性，使用 Controller 可连接不同模型和视图去满足用户的需求

## Spring MVC

SpringMVC 是一种基于 Java，实现了 Web MVC 设计模式，请求驱动类型的轻量级 Web 框架。优点如下：

- 基于组件技术。全部的应用对象，无论是控制器、视图，还是业务对象之类都是 Java 组件。并且和 Spring 提供的其他基础结构紧密集成；
- 不依赖于 Servlert API；
- 可以任意使用各种视图技术，而不仅仅局限于 jspl；
- 支持各种请求资源的映射策略；
- 易扩展。

### 原理

Spring MVC 框架是以请求为驱动，围绕  **DispatcherServlet** 设计，将请求发给控制器，然后通过模型对象，分派器来展示请求结果视图。

原理图如下：

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/java-notes/spring/springmvc_1.png" width="750px"/></div>

- 客户端发起 HTTP 请求，将请求提交到 DispatcherServlet。
- 寻找处理器：由 DispatcherServlet 查询一个或多个 HandlerMapping，找到处理该请求的 Contoller。
- 调用处理器：DispatcherServlet 将请求提交到 Controller。
- 调用业务处理逻辑并返回结果：Controller 调用业务处理逻辑后，返回 ModelAndView。
- 处理视图映射并返回模型：DispatcherServlet 查询一个或多个 ViewResolver 视图解析器，找到 ModelAndView 指定的视图。
- HTTP 响应：视图负责将结果在客户端浏览器上渲染和展示。

### 重要组件

#### DispatcherServlet

- 说明：前端控制器，不需要工程师开发，由 SpringMVC 框架提供。
- 作用：**Spring MVC 的入口。接收请求，响应结果，相当于转发器，中央处理器**。DispatcherServlet是整个流程控制的中心，由它调用其它组件处理用户的请求，**DispatcherServlet 降低了组件之间的耦合度**。

#### HandlerMapping

- 说明：处理器映射器，不需要工程师开发，由 Spring MVC 框架提供。
- 作用：**根据请求的 url 查找 Handler**。Spring MVC 提供了不同的映射器实现不同的映射方式，例如：配置文件方式，实现接口方式，注解方式等。

#### HandlerAdapter

- 说明：处理器适配器。
- 作用：**按照特定规则（HandlerAdapter要求的规则）执行 Handler**。通过 HandlerAdapter 对处理器进行执行，这是适配器模式的应用，通过扩展适配器可以对更多类型的处理器进行执行。

#### Handler

- 说明：处理器，需要工程师开发。
- 注意：编写 Handler 时按照 HandlerAdapter 的要求的规则去做，这样适配器才可以去正确执行 Handler, Handler 是**后端控制器**，在 DispatcherServlet 的控制下 Handler 对具体的用户请求进行处理。 由于 Handler 涉及到具体的用户业务请求，所以一般情况需要工程师根据业务需求开发 Handler。

#### ViewResolver

- 说明：视图解析器，不需要工程师开发，由 Spring MVC 框架提供。

- 作用：**进行视图解析，根据逻辑视图名解析成真正的视图**。ViewResolver 负责将处理结果生成 View 视图， ViewResolver 首先根据逻辑视图名解析成物理视图名即具体的页面地址，再**生成 View 视图对象**。

  Spring MVC 框架提供了很多的 View 视图类型，包括：jstlView、freemarkerView、pdfView等。 一般情况下需要通过页面标签或页面模版技术将模型数据通过页面展示给用户，需要工程师根据业务需求开发具体的页面。

#### View

- 说明：视图 View，需要工程师开发。
- 作用：**View 是一个接口，实现类支持不同的 View 类型（jsp、freemarker、pdf…）**。
